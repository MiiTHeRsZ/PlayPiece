package com.playpiece.PlayPiece.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.Services.*;

@RestController
@RequestMapping(value = "acesso")
public class LoginController {

    final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "access", params = { "email" })
    public ResponseEntity<LoginModel> getLoginInfo(@RequestParam String email) {
        return ResponseEntity.ok().body(loginService.getLoginInfo(email));
    }

}
