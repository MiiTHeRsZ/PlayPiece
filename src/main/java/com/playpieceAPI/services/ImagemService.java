package com.playpieceAPI.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.playpieceAPI.models.ImagemModel;
import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.repositories.ImagemRepository;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;
    @Autowired
    private ProdutoService produtoService;

    public ImagemModel updateImagem(Long id, ImagemModel novaImagem) {
        try {
            ImagemModel imagem = imagemRepository.findById(id).get();

            novaImagem.setImagemId(imagem.getImagemId());

            return imagemRepository.save(novaImagem);
        } catch (Exception e) {
            throw e;
        }
    }

    public ImagemModel postImagem(ImagemModel novaImagem) {
        try {
            novaImagem.setImagemId(null);

            return imagemRepository.save(novaImagem);
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveImage(List<MultipartFile> listaImagens, String tipo, Long produtoID, Long fav) throws IOException {

        try {
            ProdutoModel produto = produtoService.getProdutoById(produtoID);
            String folder = "src/main/resources/static/images/Produtos/" + produtoID + "/";
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

            for (int i = 0; i < listaImagens.size(); i++) {
                var imagem = listaImagens.get(i);

                byte[] bytes = imagem.getBytes();
                String nome = String.format("%03d", i) + "." + tipo;
                path = Paths.get(folder + nome);
                Files.write(path, bytes);
                boolean isFav = false;
                if (fav == i) {
                    isFav = true;
                } else {
                    isFav = false;
                }

                String caminho = "/src/main/resources/static/images/Produtos/" + produtoID + "/";
                ImagemModel imagemSalva = new ImagemModel(null, produto, caminho + nome, isFav, true);
                postImagem(imagemSalva);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public ImagemModel statusImagem(Long id) {
        try {
            ImagemModel imagem = imagemRepository.findById(id).get();
            imagem.setAtivo(!imagem.isAtivo());
            return imagem;
        } catch (Exception e) {
            throw e;
        }
    }
}
