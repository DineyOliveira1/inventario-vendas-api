package inventario_vendas_api.mapper;


import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDto toDto(Cliente cliente);

    Cliente toEntity(ClienteDto dto);

}

