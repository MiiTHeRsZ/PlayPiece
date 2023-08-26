package com.playpiece.PlayPiece.Services;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.LoginModel;
import com.playpiece.PlayPiece.repositories.LoginRepository;

@Service
public class LoginService {
    final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginModel getLoginInfo(String email) {
        LoginModel login = loginRepository.getLoginInfo(email);
        login.setSenha(login.getSenha().getBytes().toString());
        return login;
    }
}
