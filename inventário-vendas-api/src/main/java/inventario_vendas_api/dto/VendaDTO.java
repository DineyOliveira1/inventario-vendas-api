package inventario_vendas_api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class VendaDTO {

    private Long id;
    private Long clienteId;
    private BigDecimal valorTotal;
    private LocalDate dataVenda;
    private Set<Long> produtos;
    public VendaDTO() {}

    public VendaDTO(Long id, Long clienteId, Set<Long> produtos, BigDecimal valorTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Set<Long> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Long> produtos) {
        this.produtos = produtos;
    }
}




