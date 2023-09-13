package com.playpiece.PlayPiece.Controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playpiece.PlayPiece.Models.ProdutoModel;
import com.playpiece.PlayPiece.Services.ImagemService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/imagem")
public class ImagemController {

    final ImagemService imagemService;

    public ImagemController(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    @PostMapping(value = "/{id}", params = { "nome" })
    public String uploadImagem(@RequestBody MultipartFile imageFile, @PathVariable Long id, @RequestParam String nome) {
        String returnValue = "start";

        try {
            imagemService.saveImage(imageFile, id, nome);
        } catch (IOException e) {
            e.printStackTrace();
            returnValue = "error";
        }
        return returnValue;
    }

}
