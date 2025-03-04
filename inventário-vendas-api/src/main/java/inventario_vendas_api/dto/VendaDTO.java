package inventario_vendas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private Long clienteId;
    private Map<Long, Integer> produtosQuantidade;
    private BigDecimal valorTotal;
    private LocalDate dataVenda;

}

