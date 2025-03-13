package inventario_vendas_api.mapper;

import inventario_vendas_api.dto.ProdutoDto;
import inventario_vendas_api.entities.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "preco", target = "preco")
    ProdutoDto toDto(Produto produto);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "preco", target = "preco")
    Produto toModel(ProdutoDto produtoDto);
}