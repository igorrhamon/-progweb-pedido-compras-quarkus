package br.rraminelli.service.impl.pedido;

import br.rraminelli.model.Pedido;
import br.rraminelli.model.Produto;
import br.rraminelli.model.enums.StatusPedidoEnum;
import br.rraminelli.service.ProcessarPedidoService;
import br.rraminelli.service.ProdutoService;
import io.quarkus.arc.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.transaction.Transactional;

@Priority(30)
@ApplicationScoped
public class ProcessarPedidoBaixarEstoque implements ProcessarPedidoService {

    final ProdutoService produtoService;

    public ProcessarPedidoBaixarEstoque(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    @Transactional
    public void processar(Pedido pedido) {

        if (!StatusPedidoEnum.NOVO.equals(pedido.getStatus())) {
            return;
        }

        pedido.getItens().forEach(item -> {
            final Produto produto = produtoService.getId(item.getProduto().getId());
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoService.salvar(produto);
        });

    }

}
