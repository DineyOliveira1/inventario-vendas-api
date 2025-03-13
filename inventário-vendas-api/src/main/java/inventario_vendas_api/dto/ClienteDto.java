package inventario_vendas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class ClienteDto {

    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal limiteCredito;

    @NotNull
    private Integer diaFechamentoFatura;

    public ClienteDto(Long id, String nome, BigDecimal limiteCredito, Integer diaFechamentoFatura) {
        this.id = id;
        this.nome = nome;
        this.limiteCredito = limiteCredito;
        this.diaFechamentoFatura = diaFechamentoFatura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Integer getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    public void setDiaFechamentoFatura(Integer diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }
}
