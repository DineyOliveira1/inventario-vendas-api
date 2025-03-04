package inventario_vendas_api.mapper;


import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toModel(ProdutoDto ProdutoDTO);

    ProdutoDto toDTO(Produto produto);
}
