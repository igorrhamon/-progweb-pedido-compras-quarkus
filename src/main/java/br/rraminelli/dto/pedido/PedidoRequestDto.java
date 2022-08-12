package br.rraminelli.dto.pedido;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {

    @NotNull
    private List<ItemPedidoDto> itens;

}
