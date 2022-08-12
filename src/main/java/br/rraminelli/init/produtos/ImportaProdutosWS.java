package br.rraminelli.init.produtos;

import br.rraminelli.dto.produto.ProdutoImportListDto;
import br.rraminelli.model.Produto;
import br.rraminelli.restclient.ProdutoRestClient;
import br.rraminelli.service.ProdutoService;
import io.quarkus.arc.Priority;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Priority(20)
public class ImportaProdutosWS implements ImportaProdutos {

    final ProdutoRestClient produtoRestClient;
    final ProdutoService produtoService;

    public ImportaProdutosWS(@RestClient ProdutoRestClient produtoRestClient,
                             ProdutoService produtoService) {
        this.produtoRestClient = produtoRestClient;
        this.produtoService = produtoService;
    }

    @Override
    public void importar() throws Exception {

        ProdutoImportListDto produtoImportListDto =
                produtoRestClient.findAll("phone");

        List<Produto> produtoList =
                produtoImportListDto
                        .getProducts()
                        .stream().map(prod -> {
                                    return new Produto(
                                            null,
                                            prod.getTitle(),
                                            prod.getDescription(),
                                            prod.getPrice(),
                                            prod.getDiscountPercentage(),
                                            prod.getStock(),
                                            prod.getThumbnail()
                                    );
                                }
                        )
                        .collect(Collectors.toList());

        produtoService.salvarLista(produtoList);

    }

}
