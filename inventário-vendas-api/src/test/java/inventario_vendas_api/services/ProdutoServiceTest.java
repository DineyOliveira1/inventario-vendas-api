package inventario_vendas_api.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.mapper.ProdutoMapper;
import inventario_vendas_api.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;
    private ProdutoDto produtoDto;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produtoDto = new ProdutoDto(1L, "Produto Teste", new BigDecimal("99.99"));
        produto = new Produto(1L, "Produto Teste", new BigDecimal("99.99"));
    }

    @Test
    void deveSalvarProduto() {

        when(produtoMapper.toModel(produtoDto)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDTO(produto)).thenReturn(produtoDto);

        ProdutoDto result = produtoService.saveProduto(produtoDto);

        assertNotNull(result);
        assertEquals(produtoDto.id(), result.id());
        verify(produtoRepository, times(1)).save(produto);
        verify(produtoMapper, times(1)).toDTO(produto);
    }

    @Test
    void deveAtualizarProduto() {

        when(produtoRepository.existsById(produtoDto.id())).thenReturn(true);
        when(produtoMapper.toModel(produtoDto)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDTO(produto)).thenReturn(produtoDto);

        ProdutoDto result = produtoService.updateProduto(produtoDto.id(), produtoDto);

        assertNotNull(result);
        assertEquals(produtoDto.id(), result.id());
        verify(produtoRepository, times(1)).existsById(produtoDto.id());
        verify(produtoRepository, times(1)).save(produto);
        verify(produtoMapper, times(1)).toDTO(produto);
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoExistirParaAtualizacao() {

        when(produtoRepository.existsById(produtoDto.id())).thenReturn(false);

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.updateProduto(produtoDto.id(), produtoDto);
        });
        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void deveExcluirProduto() {

        when(produtoRepository.existsById(produtoDto.id())).thenReturn(true);

        produtoService.deleteById(produtoDto.id());

        verify(produtoRepository, times(1)).deleteById(produtoDto.id());
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoExistirParaExcluir() {

        when(produtoRepository.existsById(produtoDto.id())).thenReturn(false);

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.deleteById(produtoDto.id());
        });
        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void deveRetornarProdutoPorId() {

        when(produtoRepository.findById(produtoDto.id())).thenReturn(Optional.of(produto));

        Produto result = produtoService.findById(produtoDto.id());

        assertNotNull(result);
        assertEquals(produtoDto.id(), result.getId());
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoExistirParaBuscaPorId() {

        when(produtoRepository.findById(produtoDto.id())).thenReturn(Optional.empty());

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.findById(produtoDto.id());
        });
        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void deveRetornarTodosProdutos() {

        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        List<Produto> result = produtoService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void deveLancarExcecaoSeNaoExistiremProdutos() {

        when(produtoRepository.findAll()).thenReturn(List.of());

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.findAll();
        });
        assertEquals("Nenhum produto encontrado", exception.getMessage());
    }
}