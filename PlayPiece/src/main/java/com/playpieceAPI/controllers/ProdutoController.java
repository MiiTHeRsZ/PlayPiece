package com.playpieceAPI.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.services.ProdutoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/produto")

public class ProdutoController {

    final ProdutoService produtoService;
    final ImagemController imagemController;

    public ProdutoController(ProdutoService produtoService, ImagemController imagemController) {
        this.produtoService = produtoService;
        this.imagemController = imagemController;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getProdutoList(@PageableDefault(value = 10) Pageable pageable) {
        return new ResponseEntity<List<ProdutoModel>>(produtoService.getProdutoList(pageable).getContent(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getProdutoById(@PathVariable Long id) {
        return new ResponseEntity<ProdutoModel>(produtoService.getProdutoById(id), HttpStatus.OK);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<List<ProdutoModel>> getProdutoByNome(@RequestParam String nome,
            @PageableDefault(value = 10) Pageable pageable) {
        return new ResponseEntity<List<ProdutoModel>>(produtoService.getProdutoByNome(nome, pageable).getContent(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> postProduto(@RequestBody ProdutoModel produto) {
        return new ResponseEntity<ProdutoModel>(produtoService.postProduto(produto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(
            @PathVariable Long id, @RequestBody ProdutoModel produto) {
        return new ResponseEntity<ProdutoModel>(produtoService.updateProduto(id, produto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoModel> deleteProduto(@PathVariable Long id) {
        return new ResponseEntity<ProdutoModel>(produtoService.statusProduto(id), HttpStatus.OK);
    }

}
