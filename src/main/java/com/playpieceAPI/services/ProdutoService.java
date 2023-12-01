package com.playpieceAPI.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ImagemModel;
import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<ProdutoModel> getProdutoList(Pageable pageable) {

        try {
            int pagina = pageable.getPageNumber();
            int limite = pageable.getPageSize() <= 10 ? 10 : pageable.getPageSize();
            var atributos = ProdutoModel.class.getDeclaredFields();
            List<String> nomeAtributos = new ArrayList<>();
            for (Field atributo : atributos) {
                nomeAtributos.add(atributo.getName());
            }
            Sort organizacao = Sort.by(Direction.DESC, "produtoId");
            if (pageable.getSort() != Sort.unsorted()) {
                var sortProp = ((pageable.getSort().toString()).split(":"))[0];
                String sortDir = ((pageable.getSort().toString()).replaceAll(" ", "")).split(":")[1];
                if (nomeAtributos.contains(sortProp)) {
                    organizacao = Sort.by(Sort.Direction.valueOf(sortDir.toUpperCase()), sortProp);
                } else {
                    organizacao = Sort.by(Direction.DESC, "produtoId");
                }
            }

            Pageable page = PageRequest.of(pagina,
                    limite, organizacao);

            Page<ProdutoModel> produtos = produtoRepository.findAll(page);

            return produtos;
        } catch (Exception e) {
            throw e;
        }
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
