package com.playpieceAPI.services.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.playpieceAPI.models.CargoModel;
import com.playpieceAPI.models.ProdutoModel;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.repositories.ProdutoRepository;
import com.playpieceAPI.services.ProdutoService;
import com.playpieceAPI.services.UsuarioService;

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
