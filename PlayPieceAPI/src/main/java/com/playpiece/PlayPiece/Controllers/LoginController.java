package com.playpiece.PlayPiece.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.Services.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "acesso")

public class LoginController {

    final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "access", params = { "email" })
    public ResponseEntity<LoginModel> getUserLoginInfo(@RequestParam String email) {
        return ResponseEntity.ok().body(loginService.getUserLoginInfo(email));
    }

    @PostMapping(value = "newAccess", params = { "emailUsuario" })
    public ResponseEntity<LoginModel> postLoginUser(@RequestParam String emailUsuario) {
        return new ResponseEntity<>(loginService.postLoginUser(emailUsuario), HttpStatus.CREATED);
    }

    @PatchMapping("/{emailUsuario}")
    public ResponseEntity<LoginModel> patchLoginUser(@PathVariable String emailUsuario, @RequestBody String novaSenha) {
        return ResponseEntity.ok().body(loginService.patchLoginUser(emailUsuario, novaSenha));
    }

}
