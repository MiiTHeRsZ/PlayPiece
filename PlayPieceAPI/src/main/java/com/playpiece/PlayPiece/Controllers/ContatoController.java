package com.playpiece.playpiece.Controllers;

import com.playpiece.playpiece.Models.ContatoModel;
import com.playpiece.playpiece.Models.PessoaModel;
import com.playpiece.playpiece.Services.ContatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="contato")
public class ContatoController {

    final ContatoService contatoService;
    public ContatoController(ContatoService contatoService){
        this.contatoService = contatoService;
    }

    @GetMapping
    public ResponseEntity<List<ContatoModel>> getContatos(){
        return ResponseEntity.ok().body(contatoService.getContatos());
    }

}
