package com.playpiece.PlayPiece.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.CargoModel;
import com.playpiece.PlayPiece.Models.ContatoModel;
import com.playpiece.PlayPiece.Models.PessoaModel;
import com.playpiece.PlayPiece.Models.UsuarioModel;
import com.playpiece.PlayPiece.repositories.CargoRepository;
import com.playpiece.PlayPiece.repositories.PessoaRepository;
import com.playpiece.PlayPiece.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    final CargoRepository cargoRepository;
    final PessoaService pessoaService;
    final CargoService cargoService;
    final PessoaRepository pessoaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository,
            PessoaService pessoaService, CargoService cargoService, PessoaRepository pessoaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
        this.pessoaService = pessoaService;
        this.cargoService = cargoService;
        this.pessoaRepository = pessoaRepository;
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
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        ArrayList<UsuarioModel> novosUsuarios = new ArrayList<UsuarioModel>();

        for (UsuarioModel usuario : usuarios) {
            String userName = usuario.getPessoa().getNome().toLowerCase();
            if (userName.contains(nome.toLowerCase())) {
                novosUsuarios.add(usuario);
            }
        }

        return novosUsuarios;
    }

    public UsuarioModel postUsuario(UsuarioModel usuario) {

        if (usuario.getPessoa().getId() != null) {
            usuario.setPessoa(pessoaRepository.findById(usuario.getPessoa().getId()).get());
        } else {
            usuario.setPessoa(pessoaService.postPessoa(usuario.getPessoa()));
        }
        usuario.setId(null);
        usuario.setCargo(cargoRepository.findById(usuario.getCargo().getId()).get());
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel statusUsuario(int id) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(!usuario.getAtivo());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel patchUsuario(int id, UsuarioModel novoUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        PessoaModel novaPessoa = novoUsuario.getPessoa();
        CargoModel novoCargo = novoUsuario.getCargo();

        for (Field field : UsuarioModel.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.get(novoUsuario) != null &&
                        field.getName() != "id") {
                    if (field.getName() == "pessoa") {
                        pessoaService.patchPessoa(usuario.getPessoa().getId(), novaPessoa);
                    } else if (field.getName() == "cargo") {
                        usuario.setCargo(cargoService.patchCargo(usuario.getCargo().getId(), novoCargo));
                    } else {
                        if (novoUsuario.getPessoa().getAtivo() == false) {
                            novoUsuario.setAtivo(false);
                        }
                        field.set(usuario, field.get(novoUsuario));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return usuarioRepository.save(usuario);
    }
}
