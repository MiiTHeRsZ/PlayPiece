package com.playpiece.Services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpiece.Models.LoginDto;
import com.playpiece.Models.UsuarioModel;
import com.playpiece.repositories.CargoRepository;
import com.playpiece.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    final CargoRepository cargoRepository;
    final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<UsuarioModel> getUsuarioList() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel getUsuarioById(Long id) {

        try {
            UsuarioModel usuario = usuarioRepository.findById(id).get();
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public UsuarioModel getUsuarioByEmail(String email) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(email).get(0);
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<UsuarioModel> getUsuarioByNome(String nome) {
        List<UsuarioModel> usuarios = usuarioRepository.findByNomeContaining(nome);
        return usuarios;
    }

    public UsuarioModel postUsuario(UsuarioModel usuario) {
        var senhaCripto = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripto);
        usuario.setId(null);
        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());
        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel statusUsuario(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(!usuario.getAtivo());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel updateUsuario(Long id, UsuarioModel novoUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();

        var res = encoder.matches(novoUsuario.getSenha(), usuario.getSenha());
        if (!res) {
            var senhaCripto = encoder.encode(novoUsuario.getSenha());
            novoUsuario.setSenha(senhaCripto);
        }

        novoUsuario.setId(usuario.getId());
        novoUsuario.setEmailUsuario(usuario.getEmailUsuario());
        usuario = novoUsuario;

        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel usuarioLogin(LoginDto login) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(login.getEmail()).get(0);
            var result = encoder.matches(login.getSenha(), usuario.getSenha());
            if (result && login.getEmail().equalsIgnoreCase(usuario.getEmailUsuario())) {
                return usuario;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
