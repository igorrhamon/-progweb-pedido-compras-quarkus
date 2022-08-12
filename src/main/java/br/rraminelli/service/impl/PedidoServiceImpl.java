package br.rraminelli.service.impl;

import br.rraminelli.dto.pedido.PedidoRequestDto;
import br.rraminelli.dto.pedido.PedidoResponseDto;
import br.rraminelli.model.Cliente;
import br.rraminelli.model.ItemPedido;
import br.rraminelli.model.Pedido;
import br.rraminelli.model.Produto;
import br.rraminelli.model.enums.StatusPedidoEnum;
import br.rraminelli.repository.ClienteRepository;
import br.rraminelli.repository.PedidoRepository;
import br.rraminelli.service.PedidoService;
import br.rraminelli.service.ProcessarPedidoService;
import br.rraminelli.service.ProdutoService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    final JsonWebToken accessToken;
    final ProdutoService produtoService;
    final ClienteRepository clienteRepository;
    final PedidoRepository pedidoRepository;
    final Instance<ProcessarPedidoService> processarPedidoServiceList;

    public PedidoServiceImpl(JsonWebToken accessToken, ProdutoService produtoService,
                             ClienteRepository clienteRepository,
                             PedidoRepository pedidoRepository,
                             Instance<ProcessarPedidoService> processarPedidoServiceList) {
        this.accessToken = accessToken;
        this.produtoService = produtoService;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.processarPedidoServiceList = processarPedidoServiceList;
    }

    @Override
    @Transactional
    public PedidoResponseDto realizarPedido(final PedidoRequestDto pedidoDto) {

        final String email = accessToken.getSubject();
        final Cliente cliente = clienteRepository.findByEmail(email);

        final Pedido pedido = this.criarPedido(cliente, pedidoDto);

        pedidoRepository.persist(pedido);

        processarPedidoServiceList.forEach(procPedido -> procPedido.processar(pedido));

        return new PedidoResponseDto(pedido.getId());
    }

    private Pedido criarPedido(Cliente cliente, PedidoRequestDto pedidoDto) {
        final Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedidoEnum.NOVO);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setItens(new HashSet<>());
        pedidoDto.getItens().forEach(itemPedidoDto -> {
            final Produto produto = produtoService.getId(itemPedidoDto.getProdutoId());
            final ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setDesconto(produto.getDesconto());
            itemPedido.setPreco(produto.getPreco());
            itemPedido.setQuantidade(itemPedidoDto.getQuantidade());
            pedido.getItens().add(itemPedido);
        });
        return pedido;
    }

}
