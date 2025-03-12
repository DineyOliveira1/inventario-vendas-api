package inventario_vendas_api.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.exceptions.ClienteNotFoundException;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.mapper.ClienteMapper;
import inventario_vendas_api.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;
    private ClienteDto clienteDto;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteDto = new ClienteDto(1L, "Cliente Teste", new BigDecimal("1000.00"), 10);
        cliente = new Cliente(1L, "Cliente Teste", new BigDecimal("1000.00"), 10);
    }

    @Test
    void deveSalvarCliente() {
        when(clienteMapper.toEntity(clienteDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDto);

        ClienteDto result = clienteService.saveCliente(clienteDto);

        assertNotNull(result);
        assertEquals(clienteDto.id(), result.id());
        assertEquals(clienteDto.nome(), result.nome());
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteMapper, times(1)).toDto(cliente);
    }

    @Test
    void deveAtualizarCliente() {
        when(clienteRepository.findById(clienteDto.id())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDto);

        ClienteDto result = clienteService.updateCliente(clienteDto.id(), clienteDto);

        assertNotNull(result);
        assertEquals(clienteDto.id(), result.id());
        verify(clienteRepository, times(1)).findById(clienteDto.id());
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteMapper, times(1)).toDto(cliente);
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirParaAtualizacao() {
        when(clienteRepository.findById(clienteDto.id())).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.updateCliente(clienteDto.id(), clienteDto);
        });
        assertEquals("Cliente não encontrado para o id: " + clienteDto.id(), exception.getMessage());
    }

    @Test
    void deveExcluirCliente() {
        when(clienteRepository.existsById(clienteDto.id())).thenReturn(true);

        clienteService.deleteById(clienteDto.id());

        verify(clienteRepository, times(1)).deleteById(clienteDto.id());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirParaExcluir() {
        when(clienteRepository.existsById(clienteDto.id())).thenReturn(false);

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.deleteById(clienteDto.id());
        });
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void deveRetornarClientePorId() {
        when(clienteRepository.findById(clienteDto.id())).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(clienteDto.id());

        assertNotNull(result);
        assertEquals(clienteDto.id(), result.getClienteId());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirParaBuscaPorId() {
        when(clienteRepository.findById(clienteDto.id())).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.findById(clienteDto.id());
        });
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void deveRetornarTodosClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> result = clienteService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void deveLancarExcecaoSeNaoExistiremClientes() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            clienteService.findAll();
        });
        assertEquals("Nenhum produto encontrado", exception.getMessage());
    }
}
