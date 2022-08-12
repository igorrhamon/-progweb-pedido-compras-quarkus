package br.rraminelli.service;


import br.rraminelli.dto.produto.ProdutoListDto;
import br.rraminelli.model.Produto;
import io.quarkus.panache.common.Page;

import java.util.List;

public interface ProdutoService {

    Produto salvar(Produto produto);

    void excluir(Long idProduto);

    ProdutoListDto listar(String filtro, Integer page, Integer size);

    Produto getId(Long id);

    boolean isEmpty();

    void salvarLista(List<Produto> produtos);

}
