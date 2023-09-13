package com.playpiece.PlayPiece.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playpiece.PlayPiece.Models.ImagemModel;
import com.playpiece.PlayPiece.Models.ProdutoModel;
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

    public void saveImage(MultipartFile imageFile) throws IOException {

        String folder = "PlayPieceAPI/src/main/resources/static/images/Produtos/2/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder);
        Files.createDirectories(path);
        path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    public ImagemModel statusImagem(Long id) {
        ImagemModel imagem = imagemRepository.findById(id).get();
        imagem.setAtivo(!imagem.isAtivo());
        return imagem;
    }
}
