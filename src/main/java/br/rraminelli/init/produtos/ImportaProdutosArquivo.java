package br.rraminelli.init.produtos;

import br.rraminelli.model.Produto;
import br.rraminelli.service.ProdutoService;
import io.quarkus.arc.Priority;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Priority(10)
public class ImportaProdutosArquivo implements ImportaProdutos {

    final String fileProdutos;
    final ProdutoService produtoService;

    public ImportaProdutosArquivo(@ConfigProperty(name = "importar.produtos.file") String fileProdutos,
                                  ProdutoService produtoService) {
        this.fileProdutos = fileProdutos;
        this.produtoService = produtoService;
    }

    @Override
    public void importar() throws Exception {

        final List<Produto> produtoList = Files.lines(Path.of(fileProdutos))
                .skip(1)
                .map(linha -> {
                    final String[] linhaArray = linha.split(",");
                    return new Produto(
                            null,
                            linhaArray[5],
                            linhaArray[2],
                            new BigDecimal(linhaArray[6]),
                            new BigDecimal(linhaArray[1]),
                            Integer.valueOf(linhaArray[3]),
                            linhaArray[4]
                    );
                })
                .collect(Collectors.toList());

        produtoService.salvarLista(produtoList);

    }

}
