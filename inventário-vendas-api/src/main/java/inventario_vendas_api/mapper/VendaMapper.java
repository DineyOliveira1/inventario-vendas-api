package inventario_vendas_api.mapper;


import inventario_vendas_api.dto.VendaDTO;
import inventario_vendas_api.entities.Venda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendaMapper {

    Venda toModel(VendaDTO vendaDTO);

    VendaDTO toDTO(Venda venda);
}