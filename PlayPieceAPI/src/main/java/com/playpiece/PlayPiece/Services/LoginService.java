package com.playpiece.PlayPiece.Services;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.LoginModel;
import com.playpiece.PlayPiece.Models.PessoaModel;
import com.playpiece.PlayPiece.Models.UsuarioModel;
import com.playpiece.PlayPiece.repositories.LoginRepository;
import com.playpiece.PlayPiece.repositories.PessoaRepository;
import com.playpiece.PlayPiece.repositories.UsuarioRepository;

@Service
public class LoginService {
    final LoginRepository loginRepository;
    final UsuarioRepository usuarioRepository;
    final PessoaRepository pessoaRepository;

    public LoginService(LoginRepository loginRepository, UsuarioRepository usuarioRepository,
            PessoaRepository pessoaRepository) {
        this.loginRepository = loginRepository;
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public LoginModel getUserLoginInfo(String email) {
        LoginModel login = loginRepository.getUserLoginInfo(email);
        return login;
    }

    public LoginModel postLoginUser(String emailUsuario, String senha) {

        UsuarioModel usuario = usuarioRepository.findByEmailUsuario(emailUsuario).get(0);
        System.out.println(usuario);
        LoginModel login = new LoginModel(null, usuario.getEmailUsuario(),
                senha);
        loginRepository.postLoginUser(login.getSenha(), usuario.getPessoa().getId(), usuario.getId());
        return loginRepository.getUserLoginInfo(usuario.getEmailUsuario());
    }

    public LoginModel patchLoginUser(String emailUsuario, String novaSenha) {
        int idUsuario = usuarioRepository.findByEmailUsuario(emailUsuario).get(0).getId();
        loginRepository.patchLoginUser(novaSenha, idUsuario);
        return getUserLoginInfo(emailUsuario);
    }
}
