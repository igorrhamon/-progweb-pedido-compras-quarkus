package br.rraminelli.service;

import br.rraminelli.dto.pedido.PedidoRequestDto;
import br.rraminelli.dto.pedido.PedidoResponseDto;

public interface PedidoService {

    PedidoResponseDto realizarPedido(PedidoRequestDto pedido);

}
