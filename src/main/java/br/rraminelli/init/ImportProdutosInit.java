package br.rraminelli.init;

import br.rraminelli.init.produtos.ImportaProdutos;
import br.rraminelli.service.ProdutoService;
import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.transaction.Transactional;

@Slf4j
@ApplicationScoped
public class ImportProdutosInit  {

    final ProdutoService produtoService;
    final Instance<ImportaProdutos> importaProdutosList;

    public ImportProdutosInit(ProdutoService produtoService,
                              Instance<ImportaProdutos> importaProdutosList) {
        this.produtoService = produtoService;
        this.importaProdutosList = importaProdutosList;
    }

    public void startup() {
        log.info("Import produtos - INICIO");

        if (produtoService.isEmpty()) {
            this.importarProdutos();
        }

        log.info("Import produtos - FIM");
    }

    private void importarProdutos() {
        for (ImportaProdutos importaProdutos : importaProdutosList) {
            try {
                importaProdutos.importar();
                log.info("Produtos importados com sucesso " + importaProdutos.getClass().getSimpleName());
                break;
            } catch (Exception e) {
                log.error("Erro ao importar produtos", e);
            }
        }
    }

}
