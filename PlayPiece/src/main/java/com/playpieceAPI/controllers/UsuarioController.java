package com.playpieceAPI.controllers;

import java.util.ArrayList;
import java.util.List;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.services.UsuarioService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.services.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/usuario")
public class UsuarioController {
    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> getUsuarioList() {
        List<UsuarioModel> usuarios = usuarioService.getUsuarioList();
        if (usuarios.isEmpty())
            return new ResponseEntity<>("Não há usuários", HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {

        var usuario = usuarioService.getUsuarioById(id);
        if (usuario == null)
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }

    @PutMapping("/login")
    public ResponseEntity<?> getUsuarioByEmail(@RequestBody LoginDto login) {

        var usuario = usuarioService.usuarioLogin(login);

        if (usuario == null)
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

        List<String> resuList = new ArrayList<>();
        resuList.add(usuario.getUsuarioId().toString());
        resuList.add(usuario.getEmailUsuario());
        resuList.add(usuario.getSenha());
        resuList.add(usuario.getCargo().getCargoId().toString());
        return new ResponseEntity<>(resuList, HttpStatus.OK);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<?> getUsuarioByNome(@RequestParam String nome) {
        List<UsuarioModel> usuarios = usuarioService.getUsuarioByNome(nome);
        if (usuarios.isEmpty())
            return new ResponseEntity<>("Usuários não encontrados", HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> postUsuario(@RequestBody UsuarioModel usuario) {
        return new ResponseEntity<>(usuarioService.postUsuario(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable Long id,
            @RequestBody UsuarioModel novoUsuario) {
        return new ResponseEntity<>(usuarioService.updateUsuario(id, novoUsuario),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioModel> statusUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.statusUsuario(id), HttpStatus.OK);
    }
}
