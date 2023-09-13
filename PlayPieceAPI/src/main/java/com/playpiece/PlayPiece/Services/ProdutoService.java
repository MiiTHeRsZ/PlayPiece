package com.playpiece.PlayPiece.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playpiece.PlayPiece.Models.ImagemModel;
import com.playpiece.PlayPiece.Models.ProdutoModel;
import com.playpiece.PlayPiece.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    final ProdutoRepository produtoRepository;
    final ImagemService imagemService;

    public ProdutoService(ProdutoRepository produtoRepository, ImagemService imagemService) {
        this.produtoRepository = produtoRepository;
        this.imagemService = imagemService;
    }

    public Page<ProdutoModel> getProdutoList(Pageable pageable) {

        Page<ProdutoModel> produtos = produtoRepository.findAllByOrderByIdDesc(pageable);

        for (ProdutoModel produto : produtos) {
            produto.setListaImagens(adicionarImagensProduto(produto.getId()));
        }

        return produtos;
    }

    public List<ProdutoModel> getProdutoListImagem() {

        List<ProdutoModel> produtos = produtoRepository.findAllByOrderByIdDesc();

        for (ProdutoModel produto : produtos) {
            produto.setListaImagens(adicionarImagensProduto(produto.getId()));
        }

        return produtos;
    }

    public ProdutoModel getProdutoById(Long id) {
        ProdutoModel produto = produtoRepository.findById(id).get();
        produto.setListaImagens(adicionarImagensProduto(produto.getId()));

        return produto;
    }

    public Page<ProdutoModel> getProdutoByNome(String nome, Pageable pageable) {
        Page<ProdutoModel> produtos = produtoRepository.findByNomeContaining(nome, pageable);
        for (ProdutoModel produto : produtos) {
            produto.setListaImagens(adicionarImagensProduto(produto.getId()));
        }

        return produtos;
    }

    public ProdutoModel postProduto(ProdutoModel produto) {
        produto.setId(null);
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    public ProdutoModel updateProduto(Long id, ProdutoModel novoProduto) {
        ProdutoModel produto = getProdutoById(id);
        novoProduto.setId(produto.getId());
        List<ImagemModel> listaImagens = produto.getListaImagens();
        List<ImagemModel> listaImagensNovoProduto = novoProduto.getListaImagens();
        List<ImagemModel> novasImagens = new ArrayList<ImagemModel>();

        if (listaImagensNovoProduto.size() > listaImagens.size()) {
            int count = 0;
            while (count < listaImagens.size()) {
                novasImagens
                        .add(imagemService.updateImagem(listaImagens.get(count).getId(),
                                novoProduto.getListaImagens().get(count)));
                count++;
            }

            while (count < listaImagensNovoProduto.size()) {
                novasImagens
                        .add(imagemService.postImagem(listaImagensNovoProduto.get(count)));
                count++;
            }

        } else if (listaImagensNovoProduto.size() < listaImagens.size()) {

            int count = 0;
            while (count < listaImagensNovoProduto.size()) {
                novasImagens
                        .add(imagemService.updateImagem(listaImagensNovoProduto.get(count).getId(),
                                novoProduto.getListaImagens().get(count)));
                count++;
            }

            while (count < listaImagens.size()) {
                novasImagens
                        .add(imagemService.statusImagem(listaImagens.get(count).getId()));
                count++;
            }

        } else {
            for (int i = 0; i < listaImagens.size(); i++) {

                novasImagens
                        .add(imagemService.updateImagem(listaImagens.get(i).getId(),
                                novoProduto.getListaImagens().get(i)));
            }
        }

        novoProduto.setListaImagens(novasImagens);

        produtoRepository.save(novoProduto);

        return novoProduto;
    }

    public ProdutoModel statusProduto(Long id) {
        ProdutoModel produto = produtoRepository.findById(id).get();

        produto.setAtivo(!produto.isAtivo());
        produto.setListaImagens(adicionarImagensProduto(produto.getId()));
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

    private List<ImagemModel> adicionarImagensProduto(Long id) {
        ProdutoModel produto = produtoRepository.findById(id).get();
        List<ImagemModel> listaImagens = imagemService.getImagemList();
        List<ImagemModel> imagens = new ArrayList<ImagemModel>();

        for (ImagemModel imagem : listaImagens) {
            if (imagem.getProdutoId() == produto.getId())
                imagens.add(imagem);
        }

        return imagens;
    }
}
