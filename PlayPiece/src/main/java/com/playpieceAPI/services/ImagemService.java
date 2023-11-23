package com.playpieceAPI.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playpieceAPI.models.ImagemModel;
import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.repositories.ImagemRepository;

@Service
public class ImagemService {

    final ImagemRepository imagemRepository;
    final ProdutoService produtoService;

    public ImagemService(@Lazy ImagemRepository imagemRepository, @Lazy ProdutoService produtoService) {
        this.imagemRepository = imagemRepository;
        this.produtoService = produtoService;
    }

    public List<ImagemModel> getImagemList() {
        return imagemRepository.findAll();
    }

    public ImagemModel updateImagem(Long id, ImagemModel novaImagem) {
        ImagemModel imagem = imagemRepository.findById(id).get();

        novaImagem.setImagemId(imagem.getImagemId());

        return imagemRepository.save(novaImagem);
    }

    public ImagemModel postImagem(ImagemModel novaImagem) {
        novaImagem.setImagemId(null);

        return imagemRepository.save(novaImagem);
    }

    public void saveImage(MultipartFile imagem, Long produtoID, String nome, int fav) throws IOException {

        ProdutoModel produto = produtoService.getProdutoById(produtoID);

        String folder = "PlayPiece/src/main/resources/static/images/Produtos/" + produtoID + "/";
        byte[] bytes = imagem.getBytes();
        Path path = Paths.get(folder);
        Boolean existe = Files.exists(path);
        if (existe) {
            File diretorio = new File(folder);
            File[] arquivos = diretorio.listFiles();
            for (File arquivo : arquivos) {
                arquivo.delete();
            }
        }
        Files.deleteIfExists(path);
        Files.createDirectories(path);
        path = Paths.get(folder + nome);
        Files.write(path, bytes);
        boolean isFav = false;
        if (fav == 1) {
            isFav = true;
        } else {
            isFav = false;
        }

        String caminho = "/src/main/resources/static/images/Produtos/" + produtoID + "/";
        ImagemModel imagemSalva = new ImagemModel(null, produto, caminho + nome, isFav, true);
        postImagem(imagemSalva);
    }

    public ImagemModel statusImagem(Long id) {
        ImagemModel imagem = imagemRepository.findById(id).get();
        imagem.setAtivo(!imagem.isAtivo());
        return imagem;
    }
}
