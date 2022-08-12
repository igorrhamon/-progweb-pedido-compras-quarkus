package br.rraminelli.repository;

import br.rraminelli.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<Pedido, Long> {



}
