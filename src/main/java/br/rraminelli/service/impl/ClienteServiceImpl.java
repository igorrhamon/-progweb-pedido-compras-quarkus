package br.rraminelli.service.impl;

import br.rraminelli.model.Cliente;
import br.rraminelli.model.enums.PerfilEnum;
import br.rraminelli.repository.ClienteRepository;
import br.rraminelli.security.PasswordUtils;
import br.rraminelli.service.ClienteService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Objects;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        cliente.setPerfil(PerfilEnum.CLIENTE);
        if (Objects.isNull(cliente.getId())) {
            cliente.setSenha(PasswordUtils.encode(cliente.getSenha()));
            clienteRepository.persist(cliente);
        } else {
            Cliente clienteDb = clienteRepository.findById(cliente.getId());
            clienteDb.setNome(cliente.getNome());
            clienteDb.setCpf(cliente.getCpf());

            clienteDb.setEndereco(cliente.getEndereco());
            clienteDb.getEndereco().setId(clienteDb.getEndereco().getId());

            clienteRepository.persist(cliente);
        }
        return cliente;
    }

}
