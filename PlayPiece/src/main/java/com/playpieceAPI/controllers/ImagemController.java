package com.playpieceAPI.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playpieceAPI.services.ImagemService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/imagem")
public class ImagemController {

    final ImagemService imagemService;

    public ImagemController(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    @PostMapping(value = "/{id}", params = { "tipo", "fav" })
    public ResponseEntity<?> uploadImagem(@RequestPart("imageFiles") List<MultipartFile> imageFiles,
            @PathVariable Long id,
            @RequestParam String tipo, @RequestParam Long fav) {

        try {
            imagemService.saveImage(imageFiles, tipo, id, fav);
            return new ResponseEntity<String>("OK", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("ERRO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", params = { "tipo", "fav" })
    public ResponseEntity<?> updateImagem(@RequestBody List<MultipartFile> imageFile, @PathVariable Long id,
            @RequestParam String tipo, @RequestParam Long fav) {
        try {
            imagemService.saveImage(imageFile, tipo, id, fav);
            return new ResponseEntity<String>("OK", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("ERRO", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
