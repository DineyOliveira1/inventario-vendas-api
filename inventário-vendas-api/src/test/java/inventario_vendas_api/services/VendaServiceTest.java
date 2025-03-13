package inventario_vendas_api.services;

import inventario_vendas_api.dto.VendaDTO;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.entities.Venda;
import inventario_vendas_api.mapper.VendaMapper;
import inventario_vendas_api.repositories.ClienteRepository;
import inventario_vendas_api.repositories.ProdutoRepository;
import inventario_vendas_api.repositories.VendaRepository;
import inventario_vendas_api.exceptions.ClienteNotFoundException;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendaMapper vendaMapper;

    private Cliente cliente;
    private Produto produto;
    private VendaDTO vendaDTO;
    private Venda venda;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Cliente Teste", BigDecimal.valueOf(1000), 10);
        produto = new Produto(1L, "Produto Teste", BigDecimal.valueOf(100));
        vendaDTO = new VendaDTO(1L, new HashSet<>(Arrays.asList(1L)), cliente.getId());
        venda = new Venda();
        venda.setCliente(cliente);
        venda.setProdutos(new HashSet<>(Arrays.asList(produto)));
        venda.setValorTotal(BigDecimal.valueOf(100));
        venda.setDataVenda(LocalDate.now());
    }

    @Test
    void testExcluirVenda() {
        doNothing().when(vendaRepository).deleteById(1L);

        vendaService.excluirVenda(1L);

        verify(vendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarVendaPorId() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        when(vendaMapper.toDTO(any(Venda.class))).thenReturn(vendaDTO);

        VendaDTO result = vendaService.buscarVendaPorId(1L);

        verify(vendaRepository, times(1)).findById(1L);
        assert(result != null);
    }
}
