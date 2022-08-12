package br.rraminelli;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusAppMain {

    public static void main(String[] args) {
        Quarkus.run(PedidosComprasApplication.class, args);
    }

}
