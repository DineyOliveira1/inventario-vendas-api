package inventario_vendas_api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "limite_credito")
    private BigDecimal limiteCredito;

    @Column(name = "data_fechamento")
    private Integer diaFechamentoFatura;

    public Cliente() {
    }

    public Cliente(Long id, String nome, BigDecimal limiteCredito, Integer diaFechamentoFatura) {
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

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", limiteCredito=" + limiteCredito +
                ", diaFechamentoFatura=" + diaFechamentoFatura +
                '}';
    }
}



