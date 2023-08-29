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

    public LoginModel postLoginUser(int idUsuario) {

        UsuarioModel usuario = usuarioRepository.findById(idUsuario).get();
        System.out.println(usuario);
        LoginModel login = new LoginModel(null, usuario.getEmailUsuario(),
                usuario.getPessoa().getCpf().toString());
        System.out.println(login.getSenha() + " - " + usuario.getPessoa().getId() + " - " + idUsuario);
        loginRepository.postLoginUser(login.getSenha(), usuario.getPessoa().getId(), idUsuario);
        return loginRepository.getUserLoginInfo(usuario.getEmailUsuario());
    }
}
