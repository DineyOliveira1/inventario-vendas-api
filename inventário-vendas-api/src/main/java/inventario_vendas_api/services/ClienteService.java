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

    final private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;

    }

    public ClienteDto saveCliente(ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    public ClienteDto updateCliente(Long id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para o id: " + id));
        clienteExistente.setNome(clienteDto.nome());
        clienteExistente.setLimiteCredito(clienteDto.limiteCredito());
        clienteExistente.setDiaFechamentoFatura(clienteDto.diaFechamentoFatura());

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
            throw new ProdutoNotFoundException("Nenhum produto encontrado");
        }
        return cliente;
    }
}


