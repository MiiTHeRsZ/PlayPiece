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

    public UsuarioModel getUsuarioById(int id) {
        return usuarioRepository.findById(id).get();
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
        usuario.setId(null);
        usuario.getCargo().setId(null);
        usuario.getPessoa().setId(null);
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel statusUsuario(int id) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(!usuario.getAtivo());

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel putChangeCargo(int id, int cargoId) {
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        CargoModel novoCargo = cargoRepository.findById(cargoId).get();
        usuario.setCargo(novoCargo);
        return usuario;
    }

    public UsuarioModel patchUsuario(int id, UsuarioModel usuarioModel) {

        UsuarioModel novoUsuario = usuarioModel;
        UsuarioModel usuario = usuarioRepository.findById(id).get();
        PessoaModel pessoa = usuario.getPessoa();
        PessoaModel novaPessoa = novoUsuario.getPessoa();
        ContatoModel contato = pessoa.getContato();
        ContatoModel novoContato = novaPessoa.getContato();
        CargoModel cargo = usuario.getCargo();
        CargoModel novoCargo = novoUsuario.getCargo();

        for (Field field : UsuarioModel.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.get(novoUsuario) != null &&
                        field.getName() != "id") {

                    if (field.getName() == "pessoa") {
                        for (Field pessoaField : PessoaModel.class.getDeclaredFields()) {
                            pessoaField.setAccessible(true);

                            if (pessoaField.get(novaPessoa) != null
                                    && !pessoaField.get(novaPessoa).equals(pessoaField.get(pessoa))
                                    && pessoaField.getName() != "id" && pessoaField.getName() != "cpf") {

                                if (pessoaField.getName() == "contato") {
                                    for (Field contatoField : ContatoModel.class.getDeclaredFields()) {
                                        contatoField.setAccessible(true);

                                        if (contatoField.get(novoContato) != null
                                                && !contatoField.get(novoContato).equals(contatoField.get(contato))
                                                && contatoField.getName() != "id") {

                                            contatoField.set(contato, contatoField.get(novoContato));
                                        }
                                    }
                                } else {

                                    pessoaField.set(pessoa, pessoaField.get(novaPessoa));
                                }
                            }
                        }
                    } else if (field.getName() == "cargo") {

                        for (Field cargoField : CargoModel.class.getDeclaredFields()) {
                            cargoField.setAccessible(true);

                            if (cargoField.get(novoCargo) != null
                                    && !cargoField.get(novoCargo).equals(cargoField.get(cargo))
                                    && cargoField.getName() != "nome") {

                                novoCargo = cargoRepository.findById(usuarioModel.getCargo().getId()).get();

                                usuario.setCargo(novoCargo);
                            }
                        }

                    } else {
                        if (novoUsuario.getPessoa().getAtivo() == false) {
                            novoUsuario.setAtivo(false);
                        }
                        field.set(usuario, field.get(novoUsuario));
                    }

                }

                if (field.get(novoUsuario) != null && field.getName() == "id"
                        && !novoUsuario.getId().toString().equals(usuario.getId().toString())) {

                    System.out.println("Id não pode ser alterado!");

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return usuarioRepository.save(usuario);
    }
}
