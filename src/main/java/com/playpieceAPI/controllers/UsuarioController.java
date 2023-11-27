package com.playpieceAPI.controllers;

import java.util.ArrayList;
import java.util.List;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
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

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getUsuarioList() {
        List<UsuarioModel> usuarios = new ArrayList<>();

        try {
            usuarios = usuarioService.getUsuarioList();
            if (usuarios.isEmpty())
                return new ResponseEntity<>("Não há usuários", HttpStatus.NOT_FOUND);

            return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {

        try {

            var usuario = usuarioService.getUsuarioById(id);
            if (usuario == null)
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(usuario, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping("/login")
    public ResponseEntity<?> getUsuarioByEmail(@RequestBody LoginDto login) {

        UsuarioModel usuario;
        try {
            usuario = usuarioService.usuarioLogin(login);
            if (usuario == null)
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

            List<String> resuList = new ArrayList<>();
            resuList.add(usuario.getUsuarioId().toString());
            resuList.add(usuario.getEmailUsuario());
            resuList.add(usuario.getSenha());
            resuList.add(usuario.getCargo().getCargoId().toString());
            return new ResponseEntity<>(resuList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<?> getUsuarioByNome(@RequestParam String nome) {
        try {
            List<UsuarioModel> usuarios = usuarioService.getUsuarioByNome(nome);
            if (usuarios.isEmpty())
                return new ResponseEntity<>("Usuários não encontrados", HttpStatus.NOT_FOUND);

            return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postUsuario(@RequestBody UsuarioModel usuario) {
        try {
            UsuarioModel novoUsuario = usuarioService.postUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id,
            @RequestBody UsuarioModel novoUsuario) {

        try {
            return new ResponseEntity<>(usuarioService.updateUsuario(id, novoUsuario),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> statusUsuario(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(usuarioService.statusUsuario(id), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
