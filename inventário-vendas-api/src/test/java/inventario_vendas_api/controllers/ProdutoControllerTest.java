package inventario_vendas_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.services.ProdutoService;
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
class ProdutoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ProdutoDto produtoDto;
    private Produto produto;

    @BeforeEach
    void setUp() {
        produtoDto = new ProdutoDto(1L, "Produto Teste", new BigDecimal("99.99"));

        produto = new Produto();
        produto.setId(1L);
        produto.setDescricao("Produto Teste");
        produto.setPreco(new BigDecimal("99.99"));

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void testCreateProdutoSuccess() throws Exception {
        when(produtoService.saveProduto(any(ProdutoDto.class))).thenReturn(produtoDto);
        String jsonBody = objectMapper.writeValueAsString(produtoDto);

        mockMvc.perform(post("/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBody));
    }

    @Test
    void testUpdateProdutoSuccess() throws Exception {
        when(produtoService.updateProduto(eq(1L), any(ProdutoDto.class))).thenReturn(produtoDto);
        String jsonBody = objectMapper.writeValueAsString(produtoDto);

        mockMvc.perform(put("/produto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBody));
    }

    @Test
    void testDeleteProdutoSuccess() throws Exception {
        doNothing().when(produtoService).deleteById(1L);

        mockMvc.perform(delete("/produto/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Produto deletado com sucesso"));
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        when(produtoService.findById(1L)).thenReturn(produto);
        String expectedJson = objectMapper.writeValueAsString(produto);

        mockMvc.perform(get("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void testGetAllProdutosSuccess() throws Exception {
        List<Produto> produtos = Collections.singletonList(produto);
        when(produtoService.findAll()).thenReturn(produtos);
        String expectedJson = objectMapper.writeValueAsString(produtos);

        mockMvc.perform(get("/produto"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
