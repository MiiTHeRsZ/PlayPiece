/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playpiece.PlayPiece.Controllers;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.Services.*;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KINOO
 */
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public ResponseEntity<List<PessoaModel>> getPessoa() {

        List<PessoaModel> pessoas = usuarioService.getPessoas();

        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaModel> getPessoaById(@PathVariable int id) {
        PessoaModel pessoa = usuarioService.getPessoaById(id);

        return ResponseEntity.ok().body(pessoa);
    }

    @GetMapping(value = "search", params = { "cpf" })
    public ResponseEntity<List<PessoaModel>> getPessoaByCpf(@RequestParam Long cpf) {
        List<PessoaModel> pessoa = usuarioService.getPessoaByCpf(cpf);
        return ResponseEntity.ok().body(pessoa);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<List<PessoaModel>> getPessoaByNome(@RequestParam String nome) {
        List<PessoaModel> pessoas = usuarioService.getPessoaByNome(nome);
        return ResponseEntity.ok().body(pessoas);

    }

    @PostMapping
    public ResponseEntity<PessoaModel> postPessoa(@RequestBody PessoaModel pessoa) {
        return new ResponseEntity<>(usuarioService.postPessoa(pessoa), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PessoaModel> patchPessoa(@PathVariable int id, @RequestBody PessoaModel pessoaModel) {
        return new ResponseEntity<>(usuarioService.patchPessoa(id, pessoaModel), HttpStatus.OK);
    }

}
