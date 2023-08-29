package com.playpiece.PlayPiece.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping(value = "newAccess", params = { "idUsuario" })
    public ResponseEntity<LoginModel> postLoginUser(@RequestParam int idUsuario) {
        return ResponseEntity.ok().body(loginService.postLoginUser(idUsuario));
    }

}
