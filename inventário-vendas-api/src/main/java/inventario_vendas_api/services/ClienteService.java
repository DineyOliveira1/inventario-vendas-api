package inventario_vendas_api.services;

import inventario_vendas_api.dto.ClienteDto;
import inventario_vendas_api.entities.Cliente;
import inventario_vendas_api.exceptions.ClienteNotFoundException;
import inventario_vendas_api.exceptions.ProdutoNotFoundException;
import inventario_vendas_api.mapper.ClienteMapper;
import inventario_vendas_api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;

        }

    public ClienteDto saveCliente(ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    public ClienteDto updateCliente(Long id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para o id: " + id));
        clienteExistente.setNome(clienteDto.getNome());
        clienteExistente.setLimiteCredito(clienteDto.getLimiteCredito());
        clienteExistente.setDiaFechamentoFatura(clienteDto.getDiaFechamentoFatura());

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toDto(clienteAtualizado);
    }

    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException("Client not found");
        }
        clienteRepository.deleteById(id);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Client not found"));
    }

    public List<Cliente> findAll() {
        List<Cliente> cliente = clienteRepository.findAll();
        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException("Nenhum cliente encontrado");
        }
        return cliente;
    }

}


