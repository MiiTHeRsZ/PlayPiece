package com.playpiece.PlayPiece.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.PlayPiece.models.carrinho.CarrinhoModel;
import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;
import com.playpiece.PlayPiece.services.CarrinhoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/carrinho")
public class CarrinhoController {

    final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping(value = "search", params = { "cliente" })
    public ResponseEntity<?> getCarrinhoByClienteId(@RequestParam Long cliente) {

        CarrinhoModel carrinho = null;

        try {
            carrinho = carrinhoService.getCarrinhoByClienteId(cliente);

            if (carrinho != null) {
                return new ResponseEntity<CarrinhoModel>(carrinho, HttpStatus.OK);
            }
            throw new Exception();
        } catch (Exception e) {
            return new ResponseEntity<String>(
                    "{\"erro\":\"Carrinho não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> addItemCarrinho(@PathVariable Long idCarrinho, @RequestBody ItemCarrinhoModel item) {
        try {

            CarrinhoModel carrinho = null;

            carrinho = carrinhoService.addItemCarrinho(idCarrinho, item);

            if (carrinho != null) {
                return new ResponseEntity<CarrinhoModel>(carrinho, HttpStatus.OK);
            }

            throw new Exception();

        } catch (Exception e) {
            return new ResponseEntity<String>(
                    "{\"erro\":\"Falha ao adicionar produto\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/remove", params = { "codCarrinho", "codProduto" })
    public ResponseEntity<?> removerItemCarrinho(@RequestParam Long codCarrinho, @RequestParam Long codProduto) {

        var carrinho = carrinhoService.removerItemCarrinho(codCarrinho, codProduto);

        if (carrinho == null) {
            return new ResponseEntity<>(
                    "{\"erro\":\"Falha ao remover produto\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CarrinhoModel>(carrinho, HttpStatus.OK);
    }

}