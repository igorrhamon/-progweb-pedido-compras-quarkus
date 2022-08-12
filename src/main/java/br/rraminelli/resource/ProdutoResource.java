package br.rraminelli.resource;

import br.rraminelli.model.Produto;
import br.rraminelli.service.ProdutoService;
import io.quarkus.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/produto")
public class ProdutoResource {

    final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response salvar(@Valid Produto produto) {
        return Response.ok(produtoService.salvar(produto)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void excluir(@PathParam("id") Long id) {
        produtoService.excluir(id);
    }

    @GET
    public Response listar(@QueryParam("filtro") String filtro, @QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        return Response.ok(produtoService.listar(filtro, page, size)).build();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") Long id) {
        return Response.ok(produtoService.getId(id)).build();
    }

}
