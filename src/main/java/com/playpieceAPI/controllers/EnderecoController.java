package com.playpieceAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.EnderecoModel;
import com.playpieceAPI.services.EnderecoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnderecoById(@PathVariable Long id) {
        try {
            EnderecoModel endereco = enderecoService.getEnderecoById(id);
            if (endereco == null)
                return new ResponseEntity<String>("Nenhum endereço cadastrado", HttpStatus.NOT_FOUND);

            return new ResponseEntity<EnderecoModel>(endereco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<?> postEndereco(@PathVariable Long idCliente, @RequestBody EnderecoModel endereco) {
        try {
            EnderecoModel novoEndereco = enderecoService.postEndereco(idCliente, endereco);

            return new ResponseEntity<EnderecoModel>(novoEndereco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePadraoEndereco(@PathVariable Long id) {

        try {
            EnderecoModel novoEndereco = enderecoService.updatePadraoEndereco(id);

            return new ResponseEntity<EnderecoModel>(novoEndereco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> statusEndereco(@PathVariable Long id) {
        try {
            return new ResponseEntity<EnderecoModel>(enderecoService.statusEndereco(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}