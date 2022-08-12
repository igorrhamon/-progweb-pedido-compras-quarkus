package br.rraminelli;

import br.rraminelli.init.ImportProdutosInit;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

public class PedidosComprasApplication implements QuarkusApplication {

    final ImportProdutosInit importProdutosInit;

    public PedidosComprasApplication(ImportProdutosInit importProdutosInit) {
        this.importProdutosInit = importProdutosInit;
    }

    @Override
    public int run(String... args) throws Exception {

        this.importProdutosInit.startup();

        Quarkus.waitForExit();

        return 0;
    }
}
