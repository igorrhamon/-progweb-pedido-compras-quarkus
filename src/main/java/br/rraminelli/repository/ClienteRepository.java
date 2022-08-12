package br.rraminelli.repository;

import br.rraminelli.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Long> {

    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
