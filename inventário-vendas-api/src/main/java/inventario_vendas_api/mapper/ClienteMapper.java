package inventario_vendas_api.mapper;

import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "limiteCredito", target = "limiteCredito")
    @Mapping(source = "diaFechamentoFatura", target = "diaFechamentoFatura")
    ClienteDto toDto(Cliente cliente);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "limiteCredito", target = "limiteCredito")
    @Mapping(source = "diaFechamentoFatura", target = "diaFechamentoFatura")
    Cliente toEntity(ClienteDto dto);
}
