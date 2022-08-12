package br.rraminelli.restclient;


import br.rraminelli.dto.produto.ProdutoImportListDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterRestClient(baseUri = "https://dummyjson.com")
public interface ProdutoRestClient {

    @GET
    @Path(value = "/products/search")
    ProdutoImportListDto findAll(@QueryParam("q") String categoria);

}
