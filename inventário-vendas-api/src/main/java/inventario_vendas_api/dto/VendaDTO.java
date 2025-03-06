package inventario_vendas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


public record VendaDTO (

     Long id,
     Long clienteId,
     Map<Long, Integer> produtosQuantidade,
     BigDecimal valorTotal,
     LocalDate dataVenda) {

     }



