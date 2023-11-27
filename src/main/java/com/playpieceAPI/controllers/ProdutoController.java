package com.playpieceAPI.controllers;

import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.services.ProdutoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/produto")

public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // public ProdutoController(ProdutoService produtoService) {
    // this.produtoService = produtoService;
    // }

    @GetMapping
    public ResponseEntity<?> getProdutoList(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Pageable page = PageRequest.of(pageable.getPageNumber(), 10, Sort.unsorted());
            return new ResponseEntity<List<ProdutoModel>>(produtoService.getProdutoList(page).getContent(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProdutoById(@PathVariable Long id) {
        return new ResponseEntity<ProdutoModel>(produtoService.getProdutoById(id), HttpStatus.OK);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<?> getProdutoByNome(@RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Pageable page = PageRequest.of(pageable.getPageNumber(), 10, Sort.unsorted());
            return new ResponseEntity<List<ProdutoModel>>(produtoService.getProdutoByNome(nome, page).getContent(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postProduto(@RequestBody ProdutoModel produto) {
        try {
            return new ResponseEntity<ProdutoModel>(produtoService.postProduto(produto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduto(@PathVariable Long id, @RequestBody ProdutoModel produto) {
        try {
            return new ResponseEntity<ProdutoModel>(produtoService.updateProduto(id, produto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        try {
            return new ResponseEntity<ProdutoModel>(produtoService.statusProduto(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
