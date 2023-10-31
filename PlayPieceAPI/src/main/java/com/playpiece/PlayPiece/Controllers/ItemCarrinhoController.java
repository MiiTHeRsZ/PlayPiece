package com.playpiece.PlayPiece.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.playpiece.PlayPiece.repositories.ItemCarrinhoRepository;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "item")
public class ItemCarrinhoController {
    final ItemCarrinhoRepository itemCarrinhoRepository;

    public ItemCarrinhoController(ItemCarrinhoRepository itemCarrinhoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarrinhoById(@PathVariable Long id) {
        return new ResponseEntity<>(itemCarrinhoRepository.findAll(), HttpStatus.OK);
    }
}
