package com.playpiece.Controllers.carrinho;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.Models.carrinho.CarrinhoModel;
import com.playpiece.Services.carrinho.CarrinhoService;

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
            carrinho = carrinhoService.getCarrinhoAtivoByClienteId(cliente);

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

    @PutMapping(params = { "codProduto", "quantidade", "codCarrinho" })
    public ResponseEntity<?> addItemCarrinho(@RequestParam Long codProduto,
            @RequestParam int quantidade, @RequestParam Long codCarrinho) {
        try {

            CarrinhoModel carrinho = null;

            carrinho = carrinhoService.addItemCarrinho(codCarrinho, codProduto, quantidade);

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

    @PostMapping(value = "/update", params = { "codCarrinho", "codItem", "quantidade" })
    public ResponseEntity<?> atualizarQuantidadeItemCarrinho(@RequestParam Long codCarrinho,
            @RequestParam Long codItem,
            @RequestParam int quantidade) {

        try {
            var carrinho = carrinhoService.atualizarQuantidadeItemCarrinho(codCarrinho, codItem,
                    quantidade);

            if (carrinho == null) {
                return new ResponseEntity<>(
                        "{\"erro\":\"Falha ao remover produto\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                                + "}",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<CarrinhoModel>(carrinho, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}