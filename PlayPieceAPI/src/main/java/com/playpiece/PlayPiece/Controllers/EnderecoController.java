package com.playpiece.PlayPiece.Controllers;

import java.util.List;

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

import com.playpiece.PlayPiece.Models.EnderecoModel;
import com.playpiece.PlayPiece.Services.EnderecoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/endereco")
public class EnderecoController {

    final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity getEnderecoList() {
        List<EnderecoModel> listaEnderecos = enderecoService.getEnderecoList();
        if (listaEnderecos.isEmpty())
            return new ResponseEntity<String>("Nenhum endereço cadastrado", HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<EnderecoModel>>(listaEnderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEnderecoById(@PathVariable Long id) {
        EnderecoModel endereco = enderecoService.getEnderecoById(id);
        if (endereco == null)
            return new ResponseEntity<String>("Nenhum endereço cadastrado", HttpStatus.NOT_FOUND);

        return new ResponseEntity<EnderecoModel>(endereco, HttpStatus.OK);
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity postEndereco(@PathVariable Long idCliente, @RequestBody EnderecoModel endereco) {
        EnderecoModel novoEndereco = enderecoService.postEndereco(idCliente, endereco);
        if (novoEndereco == null)
            return new ResponseEntity<String>("Falha ao criar endereco", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<EnderecoModel>(novoEndereco, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePadraoEndereco(@PathVariable Long id) {
        EnderecoModel novoEndereco = enderecoService.updatePadraoEndereco(id);

        if (novoEndereco == null)
            return new ResponseEntity<String>("Falha ao atualizar endereço",
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<EnderecoModel>(novoEndereco, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity statusEndereco(@PathVariable Long id) {
        return new ResponseEntity<EnderecoModel>(enderecoService.statusEndereco(id), HttpStatus.OK);
    }

}