package inventario_vendas_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

