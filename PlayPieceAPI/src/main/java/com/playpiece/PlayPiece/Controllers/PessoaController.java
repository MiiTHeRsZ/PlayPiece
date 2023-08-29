package com.playpiece.PlayPiece.Controllers;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.Services.*;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/pessoa")
public class PessoaController {
    final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping()
    public ResponseEntity<List<PessoaModel>> getPessoa() {
        List<PessoaModel> pessoas = pessoaService.getPessoas();
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaModel> getPessoaById(@PathVariable int id) {
        PessoaModel pessoa = pessoaService.getPessoaById(id);

        return ResponseEntity.ok().body(pessoa);
    }

    @GetMapping(value = "search", params = { "cpf" })
    public ResponseEntity<List<PessoaModel>> getPessoaByCpf(@RequestParam Long cpf) {
        List<PessoaModel> pessoa = pessoaService.getPessoaByCpf(cpf);
        return ResponseEntity.ok().body(pessoa);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<List<PessoaModel>> getPessoaByNome(@RequestParam String nome) {
        List<PessoaModel> pessoas = pessoaService.getPessoaByNome(nome);
        return ResponseEntity.ok().body(pessoas);
    }

    @PostMapping
    public ResponseEntity<PessoaModel> postPessoa(@RequestBody PessoaModel pessoa) {
        return new ResponseEntity<>(pessoaService.postPessoa(pessoa), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PessoaModel> patchPessoa(@PathVariable int id, @RequestBody PessoaModel pessoaModel) {
        return new ResponseEntity<>(pessoaService.patchPessoa(id, pessoaModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaModel> statusPessoa(@PathVariable int id) {
        return new ResponseEntity<>(pessoaService.statusPessoa(id), HttpStatus.OK);
    }
}
