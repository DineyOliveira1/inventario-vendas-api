package inventario_vendas_api.mapper;

import inventario_vendas_api.dto.VendaDTO;
import inventario_vendas_api.entities.Produto;
import inventario_vendas_api.entities.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VendaMapper {

    VendaMapper INSTANCE = Mappers.getMapper(VendaMapper.class);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "valorTotal", target = "valorTotal")
    @Mapping(source = "dataVenda", target = "dataVenda")
    @Mapping(source = "produtos", target = "produtos", qualifiedByName = "produtosToIds")  // Mapeia os produtos
    VendaDTO toDTO(Venda venda);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clienteId", target = "cliente.id")
    @Mapping(source = "valorTotal", target = "valorTotal")
    @Mapping(source = "dataVenda", target = "dataVenda")
    @Mapping(source = "produtos", target = "produtos", qualifiedByName = "idsToProdutos")  // Mapeia os produtos
    Venda toModel(VendaDTO vendaDTO);


    @Named("produtosToIds")
    default Set<Long> produtosToIds(Set<Produto> produtos) {
        return produtos.stream().map(Produto::getId).collect(Collectors.toSet());
    }

    @Named("idsToProdutos")
    default Set<Produto> idsToProdutos(Set<Long> ids) {
        return ids.stream().map(id -> {
            Produto produto = new Produto();
            produto.setId(id);
            return produto;
        }).collect(Collectors.toSet());
    }
}