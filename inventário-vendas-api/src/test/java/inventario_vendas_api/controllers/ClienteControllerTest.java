package inventario_vendas_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ClienteDto clienteDto;
    private Cliente cliente;

    @BeforeEach
    void setUp() {

        clienteDto = new ClienteDto(1L,"João", new BigDecimal("1000.00"), 10);

        cliente = new Cliente();
        cliente.setNome("João");
        cliente.setLimiteCredito(new BigDecimal("1000.00"));
        cliente.setDiaFechamentoFatura(10);


        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testCreateClienteSuccess() throws Exception {

        when(clienteService.saveCliente(any(ClienteDto.class))).thenReturn(clienteDto);
        String jsonBody = objectMapper.writeValueAsString(clienteDto);


        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBody));
    }

    @Test
    void testUpdateClienteSuccess() throws Exception {
        when(clienteService.updateCliente(eq(1L), any(ClienteDto.class))).thenReturn(clienteDto);
        String jsonBody = objectMapper.writeValueAsString(clienteDto);

        mockMvc.perform(put("/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBody));
    }

    @Test
    void testDeleteClienteSuccess() throws Exception {
        doNothing().when(clienteService).deleteById(1L);

        mockMvc.perform(delete("/cliente/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Cliente deletado com sucesso"));
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        when(clienteService.findById(1L)).thenReturn(cliente);
        String expectedJson = objectMapper.writeValueAsString(cliente);

        mockMvc.perform(get("/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetAllClienteSuccess() throws Exception {
        List<Cliente> clientes = Collections.singletonList(cliente);
        when(clienteService.findAll()).thenReturn(clientes);
        String expectedJson = objectMapper.writeValueAsString(clientes);

        mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
