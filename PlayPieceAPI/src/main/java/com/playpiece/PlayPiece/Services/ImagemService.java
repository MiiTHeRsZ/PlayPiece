package com.playpiece.PlayPiece.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playpiece.PlayPiece.Models.ImagemModel;
import com.playpiece.PlayPiece.repositories.ImagemRepository;

@Service
public class ImagemService {

    final ImagemRepository imagemRepository;

    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }

    public List<ImagemModel> getImagemList() {
        return imagemRepository.findAll();
    }

    public ImagemModel updateImagem(Long id, ImagemModel novaImagem) {
        ImagemModel imagem = imagemRepository.findById(id).get();

        novaImagem.setId(imagem.getId());

        return imagemRepository.save(novaImagem);
    }

    public ImagemModel postImagem(ImagemModel novaImagem) {
        novaImagem.setId(null);

        return imagemRepository.save(novaImagem);
    }

    public void saveImage(MultipartFile imagem, Long produtoID, String nome, int fav) throws IOException {
        String folder = "src/main/resources/static/images/Produtos/" + produtoID + "/";
        byte[] bytes = imagem.getBytes();
        Path path = Paths.get(folder);
        Files.createDirectories(path);
        path = Paths.get(folder + nome);
        Files.write(path, bytes);
        boolean isFav = false;
        if (fav == 1) {
            isFav = true;
        } else {
            isFav = false;
        }
        ImagemModel imagemSalva = new ImagemModel(null, produtoID, folder + nome, isFav, true);
        postImagem(imagemSalva);
    }

    public ImagemModel statusImagem(Long id) {
        ImagemModel imagem = imagemRepository.findById(id).get();
        imagem.setAtivo(!imagem.isAtivo());
        return imagem;
    }
}
