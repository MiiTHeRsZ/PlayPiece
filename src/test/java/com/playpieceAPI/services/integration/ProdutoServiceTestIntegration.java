package com.playpieceAPI.services.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.playpieceAPI.models.*;
import com.playpieceAPI.repositories.*;

class ProdutoServiceTestIntegration {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testPostProduto() {
        ProdutoModel produto = new ProdutoModel(null, "The big potato", 5, "Little potato", 50.5, 10, null, true);

        ProdutoModel produtoRetorno = produtoRepository.save(produto);

        assertEquals(produto, produtoRetorno);
    }

}
