package com.playpiece.PlayPiece.Services;

import java.lang.reflect.Field;
import java.util.*;

import com.playpiece.PlayPiece.Models.ContatoModel;
import com.playpiece.PlayPiece.Models.PessoaModel;
import com.playpiece.PlayPiece.Models.UsuarioModel;
import com.playpiece.PlayPiece.repositories.ContatoRepository;
import com.playpiece.PlayPiece.repositories.PessoaRepository;
import com.playpiece.PlayPiece.repositories.UsuarioRepository;

import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    final PessoaRepository pessoaRepository;
    final UsuarioRepository usuarioRepository;
    final ContatoRepository contatoRepository;
    final ContatoService contatoService;

    public PessoaService(PessoaRepository pessoaRepository, ContatoRepository contatoRepository,
            ContatoService contatoService, UsuarioRepository usuarioRepository) {
        this.pessoaRepository = pessoaRepository;
        this.contatoRepository = contatoRepository;
        this.contatoService = contatoService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PessoaModel> getPessoas() {
        return pessoaRepository.findAll();
    }

    public PessoaModel getPessoaById(int id) {
        return pessoaRepository.findById(id);
    }

    public List<PessoaModel> getPessoaByCpf(Long cpf) {
        return pessoaRepository.findByCpfLike(cpf);
    }

    public List<PessoaModel> getPessoaByNome(String nome) {
        List<PessoaModel> pessoas = pessoaRepository.findByNomeContaining(nome);
        return pessoas;
    }

    public PessoaModel postPessoa(PessoaModel pessoa) {
        pessoa.setId(null);
        pessoa.getContato().setId(null);
        contatoRepository.save(pessoa.getContato());
        return pessoaRepository.save(pessoa);
    }

    public PessoaModel statusPessoa(int id) {
        PessoaModel pessoa = pessoaRepository.findById(id);
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        for (UsuarioModel usuario : usuarios) {
            if (usuario.getPessoa().getId() == pessoa.getId() && pessoa.getAtivo() == true) {
                pessoa.setAtivo(false);
                usuario.setAtivo(false);
                usuarioRepository.save(usuario);
                return pessoaRepository.save(pessoa);
            }
        }

        pessoa.setAtivo(!pessoa.getAtivo());
        return pessoaRepository.save(pessoa);
    }

    public PessoaModel patchPessoa(int id, PessoaModel novaPessoa) {
        PessoaModel pessoa = pessoaRepository.findById(id);
        ContatoModel novoContato = novaPessoa.getContato();

        for (Field field : PessoaModel.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.get(novaPessoa) != null && !field.get(novaPessoa).equals(field.get(pessoa))
                        && field.getName() != "id" && field.getName() != "cpf") {

                    if (field.getName() == "contato") {
                        contatoService.patchContato(pessoa.getContato().getId(), novoContato);
                    } else {
                        field.set(pessoa, field.get(novaPessoa));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return pessoaRepository.save(pessoa);
    }

}
