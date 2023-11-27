package com.playpieceAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.repositories.CargoRepository;
import com.playpieceAPI.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    final CargoRepository cargoRepository;
    final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository,
            BCryptPasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
        this.encoder = encoder;
    }

    public List<UsuarioModel> getUsuarioList() {
        List<UsuarioModel> listaUsuario = new ArrayList<>();
        try {
            listaUsuario = usuarioRepository.findAll();
            return listaUsuario;
        } catch (Exception e) {
            throw e;
        }
    }

    public UsuarioModel getUsuarioById(Long id) {

        try {
            UsuarioModel usuario = usuarioRepository.findById(id).orElse(null);
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }

    }

    public UsuarioModel getUsuarioByEmail(String email) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(email);
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public List<UsuarioModel> getUsuarioByNome(String nome) {
        List<UsuarioModel> usuarios = new ArrayList<>();

        try {
            usuarios = usuarioRepository.findByNomeContaining(nome);
            return usuarios;
        } catch (Exception e) {
            throw e;
        }
    }

    public UsuarioModel postUsuario(UsuarioModel usuario) {
        try {
            var senhaCripto = encoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCripto);
            usuario.setUsuarioId(null);
            usuario.setCargo(cargoRepository.findById(usuario.getCargo().getCargoId()).get());
            usuario.setAtivo(true);

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw e;
        }
    }

    public UsuarioModel statusUsuario(Long id) {

        try {
            UsuarioModel usuario = usuarioRepository.findById(id).get();
            usuario.setAtivo(!usuario.getAtivo());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw e;
        }
    }

    public UsuarioModel updateUsuario(Long id, UsuarioModel novoUsuario) {
        try {

            UsuarioModel usuario = usuarioRepository.findById(id).get();

            var res = encoder.matches(novoUsuario.getSenha(), usuario.getSenha());
            if (!res) {
                var senhaCripto = encoder.encode(novoUsuario.getSenha());
                novoUsuario.setSenha(senhaCripto);
            }

            novoUsuario.setUsuarioId(usuario.getUsuarioId());
            novoUsuario.setEmailUsuario(usuario.getEmailUsuario());
            usuario = novoUsuario;

            usuario.setCargo(cargoRepository.findById(usuario.getCargo().getCargoId()).get());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw e;
        }
    }

    public UsuarioModel usuarioLogin(LoginDto login) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(login.getEmail());
            if (usuario != null) {
                var result = encoder.matches(login.getSenha(), usuario.getSenha());
                if (result && login.getEmail().equalsIgnoreCase(usuario.getEmailUsuario())) {
                    return usuario;
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }
}
