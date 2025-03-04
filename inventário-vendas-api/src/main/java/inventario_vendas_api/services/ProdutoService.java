package inventario_vendas_api.services;


import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.mapper.ProdutoMapper;
import inventario_vendas_api.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;


    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoDto saveProduto(ProdutoDto produtoDto) {
        Produto produto = produtoMapper.toModel(produtoDto);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public ProdutoDto updateProduto(Long id, ProdutoDto produtoDto) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto not found");
        }
        Produto produto = produtoMapper.toModel(produtoDto);
        produto.setId(id);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public void deleteById(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto not found");
        }
        produtoRepository.deleteById(id);
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto not found"));
    }

    public List<Produto> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            throw new ProdutoNotFoundException("Nenhum produto encontrado");
        }
        return produtos;
    }
}

