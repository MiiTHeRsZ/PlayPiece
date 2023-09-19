package com.playpiece.PlayPiece.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.UsuarioModel;
import com.playpiece.PlayPiece.repositories.CargoRepository;
import com.playpiece.PlayPiece.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    final CargoRepository cargoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public List<UsuarioModel> getUsuarioList() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel getUsuarioById(Long id) {
        
        try{
            UsuarioModel usuario = usuarioRepository.findById(id).get();
            return usuario;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        
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

    public UsuarioModel statusUsuario(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(!usuario.getAtivo());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel updateUsuario(Long id, UsuarioModel novoUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();

        novoUsuario.setId(usuario.getId());
        novoUsuario.setEmailUsuario(usuario.getEmailUsuario());
        usuario = novoUsuario;

        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());

        return usuarioRepository.save(usuario);
    }
}
