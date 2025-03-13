package inventario_vendas_api.controllers;

import inventario_vendas_api.dto.VendaDTO;
import inventario_vendas_api.services.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaControllerTest {

    @InjectMocks
    private VendaController vendaController;

    @Mock
    private VendaService vendaService;

    private VendaDTO vendaDTO;

    @BeforeEach
    void setUp() {
        vendaDTO = new VendaDTO(1L, 1L, Set.of(101L, 102L), new BigDecimal("500.00"));
    }

    @Test
    void testCriarVenda() {
        when(vendaService.criarVenda(any(VendaDTO.class))).thenReturn(vendaDTO);

        ResponseEntity<VendaDTO> response = vendaController.criarVenda(vendaDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vendaDTO, response.getBody());
    }

    @Test
    void testListarVendas() {
        when(vendaService.listarVendas()).thenReturn(List.of(vendaDTO));

        ResponseEntity<List<VendaDTO>> response = vendaController.listarVendas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testBuscarVendaPorId() {
        when(vendaService.buscarVendaPorId(1L)).thenReturn(vendaDTO);

        ResponseEntity<VendaDTO> response = vendaController.buscarVendaPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vendaDTO, response.getBody());
    }

    @Test
    void testExcluirVenda() {
        doNothing().when(vendaService).excluirVenda(1L);

        ResponseEntity<Void> response = vendaController.excluirVenda(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testAtualizarVenda() {
        when(vendaService.atualizarVenda(eq(1L), any(VendaDTO.class))).thenReturn(vendaDTO);

        ResponseEntity<VendaDTO> response = vendaController.atualizarVenda(1L, vendaDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vendaDTO, response.getBody());
    }
}
