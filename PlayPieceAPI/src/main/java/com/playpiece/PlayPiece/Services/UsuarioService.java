package com.playpiece.PlayPiece.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.CargoModel;
import com.playpiece.PlayPiece.Models.UsuarioModel;
import com.playpiece.PlayPiece.repositories.CargoRepository;
import com.playpiece.PlayPiece.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    final CargoRepository cargoRepository;
    final CargoService cargoService;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository,
            CargoService cargoService) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
        this.cargoService = cargoService;
    }

    public List<UsuarioModel> getUsuarioList() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel getUsuarioById(int id) {
        return usuarioRepository.findById(id).get();
    }

    public UsuarioModel getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmailUsuario(email).get(0);
    }

    public List<UsuarioModel> getUsuarioByNome(String nome) {
        List<UsuarioModel> usuarios = usuarioRepository.findByNomeContaining(nome);
        return usuarios;
    }

    public UsuarioModel postUsuario(UsuarioModel usuario) {
        usuario.setId(null);
        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());
        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel statusUsuario(int id) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(!usuario.getAtivo());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel updateUsuario(int id, UsuarioModel novoUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();

        novoUsuario.setId(usuario.getId());
        novoUsuario.setEmailUsuario(usuario.getEmailUsuario());
        usuario = novoUsuario;

        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());

        return usuarioRepository.save(usuario);
    }
}
