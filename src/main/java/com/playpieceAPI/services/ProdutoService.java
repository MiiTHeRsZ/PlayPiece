package com.playpieceAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ImagemModel;
import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    final ProdutoRepository produtoRepository;
    final ImagemService imagemService;

    public ProdutoService(@Lazy ProdutoRepository produtoRepository, @Lazy ImagemService imagemService) {
        this.produtoRepository = produtoRepository;
        this.imagemService = imagemService;
    }

    public Page<ProdutoModel> getProdutoList(Pageable pageable) {

        Page<ProdutoModel> produtos = produtoRepository.findAllByOrderByProdutoIdDescAndImagemPadrao(pageable);

        return produtos;
    }

    public List<ProdutoModel> getProdutoListImagem() {

        List<ProdutoModel> produtos = produtoRepository.findAllByOrderByProdutoIdDescAndImagemPadrao();

        return produtos;
    }

    public ProdutoModel getProdutoById(Long id) {
        ProdutoModel produto = produtoRepository.findByIdOrderByPadrao(id);

        return produto;
    }

    public Page<ProdutoModel> getProdutoByNome(String nome, Pageable pageable) {
        Page<ProdutoModel> produtos = produtoRepository.findByNomeContaining(nome, pageable);

        return produtos;
    }

    public ProdutoModel postProduto(ProdutoModel produto) {
        produto.setProdutoId(null);
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    public ProdutoModel updateProduto(Long id, ProdutoModel novoProduto) {
        ProdutoModel produto = getProdutoById(id);
        novoProduto.setProdutoId(produto.getProdutoId());
        novoProduto.setListaImagens(new ArrayList<>());
        limparImagensDoProduto(produto.getProdutoId());
        produtoRepository.save(novoProduto);

        return novoProduto;
    }

    public ProdutoModel statusProduto(Long id) {
        ProdutoModel produto = produtoRepository.findById(id).get();

        produto.setAtivo(!produto.isAtivo());
        List<ImagemModel> listaImagem = produto.getListaImagens();

        if (!produto.isAtivo()) {
            for (ImagemModel imagem : listaImagem) {
                imagem.setAtivo(false);
            }
        } else {
            for (ImagemModel imagem : listaImagem) {
                imagem.setAtivo(true);
            }
        }

        return produtoRepository.save(produto);
    }

    public ProdutoModel limparImagensDoProduto(Long idProduto) {
        var produto = getProdutoById(idProduto);

        try {
            produtoRepository.delByIdProduto(produto.getProdutoId());
            produto.setListaImagens(new ArrayList<>());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Falha ao limpar imagens!");
        }

        return produtoRepository.save(produto);
    }
}
