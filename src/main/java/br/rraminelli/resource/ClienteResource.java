package br.rraminelli.resource;

import br.rraminelli.model.Cliente;
import br.rraminelli.service.ClienteService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/cliente")
public class ClienteResource {

    final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(description = "Salvar um cliente")
    @POST
    public Response salvar(@Valid Cliente cliente) {
        return Response.ok(clienteService.salvar(cliente)).build();
    }

}
