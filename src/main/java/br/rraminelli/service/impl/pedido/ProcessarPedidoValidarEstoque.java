package br.rraminelli.service.impl.pedido;

import br.rraminelli.model.Pedido;
import br.rraminelli.model.Produto;
import br.rraminelli.model.enums.StatusPedidoEnum;
import br.rraminelli.service.ProcessarPedidoService;
import br.rraminelli.service.ProdutoService;
import io.quarkus.arc.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.transaction.Transactional;

@Priority(40)
@ApplicationScoped
public class ProcessarPedidoValidarEstoque implements ProcessarPedidoService {

    final ProdutoService produtoService;

    public ProcessarPedidoValidarEstoque(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    @Transactional
    public void processar(Pedido pedido) {

        pedido.getItens().forEach(item -> {
            final Produto produto = produtoService.getId(item.getProduto().getId());
            if (produto.getEstoque() < item.getQuantidade()) {
                pedido.setStatus(StatusPedidoEnum.CANCELADO);
                pedido.setMensagemStatus("Quantidade do produto " + produto.getNome() + " nao disponivel em estoque");
            }
        });

    }

}
