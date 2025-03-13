package inventario_vendas_api.services;

import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.exceptions.ClienteNotFoundException;
import inventario_vendas_api.mapper.ClienteMapper;

import inventario_vendas_api.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    private Cliente cliente;
    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setLimiteCredito(new BigDecimal("1500.00"));
        cliente.setDiaFechamentoFatura(5);

        clienteDto = new ClienteDto(1L, "Cliente Teste", new BigDecimal("1500.00"), 5);
    }

    @Test
    void testSaveCliente() {
        when(clienteMapper.toEntity(clienteDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDto);

        ClienteDto result = clienteService.saveCliente(clienteDto);

        assertNotNull(result);
        assertEquals(clienteDto.getId(), result.getId());
        assertEquals(clienteDto.getNome(), result.getNome());
        assertEquals(clienteDto.getLimiteCredito(), result.getLimiteCredito());
    }

    @Test
    void testUpdateCliente_Success() {

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toEntity(clienteDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDto);

        ClienteDto result = clienteService.updateCliente(1L, clienteDto);

        assertNotNull(result);
        assertEquals(clienteDto.getNome(), result.getNome());
        assertEquals(clienteDto.getLimiteCredito(), result.getLimiteCredito());
        assertEquals(clienteDto.getDiaFechamentoFatura(), result.getDiaFechamentoFatura());
    }

    @Test
    void testUpdateCliente_NotFound() {
        ClienteDto updatedClienteDto = new ClienteDto(1L, "Cliente Atualizado", new BigDecimal("2000.00"), 10);

        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.updateCliente(1L, updatedClienteDto);
        });

        assertEquals("Cliente não encontrado para o id: 1", exception.getMessage());
    }

    @Test
    void testDeleteCliente_Success() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.deleteById(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCliente_NotFound() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.deleteById(1L);
        });

        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void testFindById_Success() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(1L);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
    }

    @Test
    void testFindById_NotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.findById(1L);
        });

        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void testFindAll_Success() {
        List<Cliente> clientes = List.of(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindAll_NotFound() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.findAll();
        });

        assertEquals("Nenhum cliente encontrado", exception.getMessage());
    }
}