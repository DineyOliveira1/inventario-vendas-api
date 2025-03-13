package inventario_vendas_api.services;

import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.mapper.ProdutoMapper;
import inventario_vendas_api.repositories.ProdutoRepository;
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

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    private Produto produto;
    private ProdutoDto produtoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produto = new Produto();
        produto.setId(1L);
        produto.setDescricao("Produto Teste");
        produto.setPreco(new BigDecimal("50.00"));

        produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setPreco(new BigDecimal("50.00"));
    }

    @Test
    void testSaveProduto() {
        when(produtoMapper.toModel(produtoDto)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDto(produto)).thenReturn(produtoDto);

        ProdutoDto result = produtoService.saveProduto(produtoDto);

        assertNotNull(result);
        assertEquals(produtoDto.getDescricao(), result.getDescricao());
        assertEquals(produtoDto.getPreco(), result.getPreco());
    }

    @Test
    void testUpdateProduto_Success() {
        ProdutoDto updatedProdutoDto = new ProdutoDto("Produto Atualizado", new BigDecimal("60.00"));

        Produto updatedProduto = new Produto();
        updatedProduto.setId(1L);
        updatedProduto.setDescricao("Produto Atualizado");
        updatedProduto.setPreco(new BigDecimal("60.00"));

        when(produtoRepository.existsById(1L)).thenReturn(true);
        when(produtoMapper.toModel(updatedProdutoDto)).thenReturn(updatedProduto);
        when(produtoRepository.save(updatedProduto)).thenReturn(updatedProduto);
        when(produtoMapper.toDto(updatedProduto)).thenReturn(updatedProdutoDto);

        ProdutoDto result = produtoService.updateProduto(1L, updatedProdutoDto);

        assertNotNull(result);
        assertEquals(updatedProdutoDto.getDescricao(), result.getDescricao());
        assertEquals(updatedProdutoDto.getPreco(), result.getPreco());
    }

    @Test
    void testUpdateProduto_NotFound() {
        ProdutoDto updatedProdutoDto = new ProdutoDto("Produto Atualizado", new BigDecimal("60.00"));

        when(produtoRepository.existsById(1L)).thenReturn(false);

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.updateProduto(1L, updatedProdutoDto);
        });

        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void testDeleteProduto_Success() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        produtoService.deleteById(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduto_NotFound() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.deleteById(1L);
        });

        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void testFindById_Success() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto result = produtoService.findById(1L);

        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
        assertEquals(produto.getDescricao(), result.getDescricao());
    }

    @Test
    void testFindById_NotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.findById(1L);
        });

        assertEquals("Produto not found", exception.getMessage());
    }

    @Test
    void testFindAll_Success() {
        List<Produto> produtos = List.of(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindAll_NotFound() {
        when(produtoRepository.findAll()).thenReturn(List.of());

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.findAll();
        });

        assertEquals("Nenhum produto encontrado", exception.getMessage());
    }
}
