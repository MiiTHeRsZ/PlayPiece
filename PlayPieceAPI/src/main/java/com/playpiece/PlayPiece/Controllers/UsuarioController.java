package com.playpiece.PlayPiece.Controllers;

import java.util.List;

import com.playpiece.PlayPiece.Models.UsuarioModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.playpiece.PlayPiece.Services.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/usuario")
public class UsuarioController {
    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getUsuarioList() {
        return new ResponseEntity<List<UsuarioModel>>(usuarioService.getUsuarioList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable int id) {
        return new ResponseEntity<UsuarioModel>(usuarioService.getUsuarioById(id), HttpStatus.OK);
    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<List<UsuarioModel>> getUsuarioByNome(@RequestParam String nome) {
        List<UsuarioModel> usuarios = usuarioService.getUsuarioByNome(nome);
        if (usuarios.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> postUsuario(@RequestBody UsuarioModel usuario) {
        return new ResponseEntity<>(usuarioService.postUsuario(usuario), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioModel> patchUsuario(@PathVariable int id,
            @RequestBody UsuarioModel usuarioModel) {
        return new ResponseEntity<>(usuarioService.patchUsuario(id, usuarioModel),
                HttpStatus.OK);
    }

    @PutMapping(value = "{id}", params = { "cargo" })
    public ResponseEntity<UsuarioModel> putChangeCargo(@PathVariable int id,
            @RequestParam int cargoId) {
        return new ResponseEntity<>(usuarioService.putChangeCargo(id, cargoId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioModel> statusUsuario(@PathVariable int id) {
        return new ResponseEntity<>(usuarioService.statusUsuario(id), HttpStatus.OK);
    }
}
