package br.rraminelli.service;

import br.rraminelli.model.Cliente;

public interface ClienteService {

    Cliente findByEmail(String email);

    Cliente salvar(Cliente cliente);

}
