package com.playpiece.PlayPiece.Services;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public ImagemModel statusImagem(Long id) {
        ImagemModel imagem = imagemRepository.findById(id).get();
        imagem.setAtivo(!imagem.isAtivo());
        return imagem;
    }
}
