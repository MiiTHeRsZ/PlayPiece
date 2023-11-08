package com.playpieceAPI.controllers.carrinho;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.repositories.carrinho.ItemCarrinhoRepository;
import com.playpieceAPI.services.carrinho.ItemCarrinhoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "itemcarrinho")
public class ItemCarrinhoController {
    final ItemCarrinhoRepository itemCarrinhoRepository;
    final ItemCarrinhoService itemCarrinhoService;

    public ItemCarrinhoController(ItemCarrinhoRepository itemCarrinhoRepository,
            ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.itemCarrinhoService = itemCarrinhoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemCarrinhoById(@PathVariable Long id) {

        try {
            var item = itemCarrinhoService.getItemCarrinhoById(id);

            return new ResponseEntity<>(item, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ! adicionar como parâmetro o idCliente
    @PostMapping(value = "/create", params = { "codProduto", "quantidade" })
    public ResponseEntity<?> criarItemCarrinho(@RequestParam Long codProduto,
            @RequestParam int quantidade) {
        try {
            var novoItem = itemCarrinhoService.criarItemCarrinho(codProduto, quantidade);
            return new ResponseEntity<ItemCarrinhoModel>(novoItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
