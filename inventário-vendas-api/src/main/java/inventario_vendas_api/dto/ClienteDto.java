package inventario_vendas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ClienteDto(
        @NotBlank String nome,
        @NotNull BigDecimal limiteCredito,
        @NotNull Integer diaFechamentoFatura) {
}
