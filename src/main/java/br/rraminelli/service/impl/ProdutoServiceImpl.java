package br.rraminelli.service.impl;

import br.rraminelli.dto.produto.ProdutoListDto;
import br.rraminelli.exceptions.ValidacaoException;
import br.rraminelli.model.Produto;
import br.rraminelli.repository.ProdutoRepository;
import br.rraminelli.service.ProdutoService;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    @CacheInvalidateAll(cacheName = "produtos")
    public Produto salvar(Produto produto) {
        if (Objects.isNull(produto.getId())) {
            produtoRepository.persist(produto);
        } else {
            Produto produtoDb = produtoRepository.findById(produto.getId());
            produtoDb.setNome(produto.getNome());
            produtoDb.setDescricao(produto.getDescricao());
            produtoDb.setFoto(produto.getFoto());
            produtoDb.setEstoque(produto.getEstoque());
            produtoDb.setDesconto(produto.getDesconto());
            produtoRepository.persist(produtoDb);
        }
        return produto;
    }

    @Override
    @Transactional
    public void excluir(Long idProduto) {
        produtoRepository.deleteById(idProduto);
    }

    @Override
    @Transactional(value = Transactional.TxType.NOT_SUPPORTED)
    @CacheResult(cacheName = "produtos")
    public ProdutoListDto listar(@CacheKey String filtro, @CacheKey Integer page, @CacheKey Integer size) {

        PanacheQuery<Produto> produtoPanacheQuery = produtoRepository.findByNomeOrDescricao(filtro, Page.of(page, size));

        return ProdutoListDto.builder()
                .list(produtoPanacheQuery.list())
                .size(produtoPanacheQuery.list().size())
                .page(page)
                .total(produtoPanacheQuery.pageCount())
                .build();

    }

    @Override
    @Transactional(value = Transactional.TxType.NOT_SUPPORTED)
    public Produto getId(Long id) {
        return produtoRepository.findByIdOptional(id)
                .orElseThrow(() -> new ValidacaoException("ID nao encontrado"));
    }

    @Override
    @Transactional
    public boolean isEmpty() {
        return produtoRepository.count() == 0;
    }

    @Override
    @Transactional
    public void salvarLista(List<Produto> produtos) {
        produtoRepository.persist(produtos);
    }
}
