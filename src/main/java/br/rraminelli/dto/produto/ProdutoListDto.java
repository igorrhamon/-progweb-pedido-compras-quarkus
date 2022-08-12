package br.rraminelli.dto.produto;

import br.rraminelli.model.Produto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProdutoListDto {

    private List<Produto> list;
    private int page;
    private int size;
    private int total;

}
