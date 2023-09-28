package com.playpiece.PlayPiece.Controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping(value = "/{id}", params = { "nome", "fav" })
    public ResponseEntity uploadImagem(@RequestBody MultipartFile imageFile, @PathVariable Long id,
            @RequestParam String nome, @RequestParam int fav) {

        try {
            imagemService.saveImage(imageFile, id, nome, fav);
            return new ResponseEntity("OK", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("ERRO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", params = { "nome", "fav" })
    public ResponseEntity updateImagem(@RequestBody MultipartFile imageFile, @PathVariable Long id,
            @RequestParam String nome, @RequestParam int fav) {
        try {
            imagemService.saveImage(imageFile, id, nome, fav);
            return new ResponseEntity("OK", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("ERRO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
