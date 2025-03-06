package inventario_vendas_api.services;


import inventario_vendas_api.dto.VendaDTO;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.entities.Venda;
import inventario_vendas_api.exceptions.LimiteCreditoExcedidoException;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.exceptions.VendaException;
import inventario_vendas_api.mapper.VendaMapper;
import inventario_vendas_api.repositories.ClienteRepository;
import inventario_vendas_api.repositories.ProdutoRepository;
import inventario_vendas_api.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaMapper vendaMapper;

    public VendaDTO criarVenda(VendaDTO vendaDTO) {

        validarProdutosRepetidos(vendaDTO.produtosQuantidade());

        validarLimiteCredito(vendaDTO);

        Venda venda = new Venda();

        Cliente cliente = clienteRepository.findById(vendaDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        venda.setCliente(cliente);

        venda.setDataVenda(LocalDate.now());

        Set<Produto> produtos = new HashSet<>();
        for (Long produtoId : vendaDTO.produtosQuantidade().keySet()) {
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado"));
            produtos.add(produto);
        }
        venda.setProdutos(produtos);

        BigDecimal valorTotal = calcularValorTotal(vendaDTO.produtosQuantidade());
        venda.setValorTotal(valorTotal);

        venda = vendaRepository.save(venda);

        return vendaMapper.toDTO(venda);
    }

    public List<VendaDTO> listarVendas() {
        return vendaRepository.findAll().stream()
                .map(vendaMapper::toDTO)
                .toList();
    }

    public VendaDTO buscarVendaPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaException("Venda não encontrada"));
        return vendaMapper.toDTO(venda);
    }

    public void excluirVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    public VendaDTO atualizarVenda(Long id, VendaDTO vendaDTO) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaException("Venda não encontrada"));

        validarProdutosRepetidos(vendaDTO.produtosQuantidade());

        validarLimiteCredito(vendaDTO);

        Cliente cliente = clienteRepository.findById(vendaDTO.clienteId())
                .orElseThrow(() -> new VendaException("Cliente não encontrado"));
        venda.setCliente(cliente);

        Set<Produto> produtos = new HashSet<>();
        for (Long produtoId : vendaDTO.produtosQuantidade().keySet()) {
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
            produtos.add(produto);
        }
        venda.setProdutos(produtos);

        BigDecimal valorTotal = calcularValorTotal(vendaDTO.produtosQuantidade());
        venda.setValorTotal(valorTotal);

        venda = vendaRepository.save(venda);

        return vendaMapper.toDTO(venda);
    }

    private void validarProdutosRepetidos(Map<Long, Integer> produtosQuantidade) {
        Set<Long> produtosIds = new HashSet<>();
        for (Long produtoId : produtosQuantidade.keySet()) {
            if (!produtosIds.add(produtoId)) {
                throw new ProdutoNotFoundException("Produto repetido na venda: " + produtoId);
            }
        }
    }

    private void validarLimiteCredito(VendaDTO vendaDTO) {
        Cliente cliente = clienteRepository.findById(vendaDTO.clienteId())
                .orElseThrow(() -> new VendaException("Cliente não encontrado"));

        BigDecimal valorTotalVenda = calcularValorTotal(vendaDTO.produtosQuantidade());
        BigDecimal limiteCreditoDisponivel = calcularLimiteCreditoDisponivel(cliente);

        if (valorTotalVenda.compareTo(limiteCreditoDisponivel) > 0) {
            LocalDate proximoFechamento = calcularProximoFechamento(cliente.getDiaFechamentoFatura());
            throw new LimiteCreditoExcedidoException(
                    "Limite de crédito excedido. Valor disponível: " + limiteCreditoDisponivel +
                            ". Próximo fechamento: " + proximoFechamento
            );
        }
    }

    private BigDecimal calcularValorTotal(Map<Long, Integer> produtosQuantidade) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : produtosQuantidade.entrySet()) {
            Produto produto = produtoRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
            valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return valorTotal;
    }

    private BigDecimal calcularLimiteCreditoDisponivel(Cliente cliente) {
        LocalDate dataFechamento = calcularUltimoFechamento(cliente.getDiaFechamentoFatura());
        BigDecimal totalComprasAposFechamento = vendaRepository.calcularTotalComprasAposFechamento(
                cliente.getId(), dataFechamento
        );
        return cliente.getLimiteCredito().subtract(totalComprasAposFechamento);
    }

    private LocalDate calcularUltimoFechamento(Integer diaFechamento) {
        LocalDate hoje = LocalDate.now();
        LocalDate fechamento = LocalDate.of(hoje.getYear(), hoje.getMonth(), diaFechamento);
        return hoje.isAfter(fechamento) ? fechamento : fechamento.minusMonths(1);
    }

    private LocalDate calcularProximoFechamento(Integer diaFechamento) {
        LocalDate hoje = LocalDate.now();
        LocalDate fechamento = LocalDate.of(hoje.getYear(), hoje.getMonth(), diaFechamento);
        return hoje.isAfter(fechamento) ? fechamento.plusMonths(1) : fechamento;
    }
}






