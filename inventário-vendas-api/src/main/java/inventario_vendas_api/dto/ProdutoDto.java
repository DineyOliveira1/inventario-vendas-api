package inventario_vendas_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record ProdutoDto(
        @JsonProperty("id") Long id,
        @JsonProperty("descricao") String descricao,
        @JsonProperty("preco") BigDecimal preco
) {}