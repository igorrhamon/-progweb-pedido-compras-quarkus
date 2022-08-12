package br.rraminelli.resource;

import br.rraminelli.dto.pedido.PedidoRequestDto;
import br.rraminelli.service.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pedido")
public class PedidoResource {

    final PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(description = "Realizar pedido de compras")
    @POST
    @RolesAllowed("CLIENTE")
    public Response realizarPedido(@Valid PedidoRequestDto pedidoRequestDto) {
        return Response.ok(pedidoService.realizarPedido(pedidoRequestDto)).build();
    }


}
