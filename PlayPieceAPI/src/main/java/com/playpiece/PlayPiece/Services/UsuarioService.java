package com.playpiece.playpiece.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.playpiece.playpiece.Models.ContatoModel;
import com.playpiece.playpiece.Models.PessoaModel;
import com.playpiece.playpiece.repositories.ContatoRepository;
import com.playpiece.playpiece.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService {
    final UsuarioRepository usuarioRepository;
    final ContatoRepository contatoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ContatoRepository contatoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.contatoRepository = contatoRepository;
    }

    public List<PessoaModel> getPessoas() {
        return usuarioRepository.findAll();
    }

    public PessoaModel getPessoaById(int id) {
        return usuarioRepository.findById(id);
    }

    public List<PessoaModel> getPessoaByCpf(Long cpf) {
        return usuarioRepository.findByCpfLike(cpf);
    }

    public List<PessoaModel> getPessoaByNome(String nome) {
        List<PessoaModel> pessoas = usuarioRepository.findByNomeContaining(nome);
        return pessoas;
    }

    public PessoaModel postPessoa(PessoaModel pessoa) {
        pessoa.setId(null);
        pessoa.getContato().setId(null);
        return usuarioRepository.save(pessoa);
    }

    public PessoaModel patchPessoa(int id, PessoaModel pessoaModel) {

        PessoaModel novaPessoa = pessoaModel;
        PessoaModel pessoa = usuarioRepository.findById(id);
        ContatoModel contato = pessoa.getContato();
        ContatoModel novoContato = novaPessoa.getContato();

        for (Field field : PessoaModel.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.get(novaPessoa) != null && !field.get(novaPessoa).equals(field.get(pessoa))
                        && field.getName() != "id" && field.getName() != "cpf") {

                    if (field.getName() == "contato") {
                        for (Field contatoField : ContatoModel.class.getDeclaredFields()) {
                            contatoField.setAccessible(true);

                            if (contatoField.get(novoContato) != null
                                    && !contatoField.get(novoContato).equals(contatoField.get(contato))
                                    && contatoField.getName() != "id") {

                                System.out.println("Sub contato : " + contatoField.get(contato) + " para: "
                                        + contatoField.get(novoContato));

                                contatoField.set(contato, contatoField.get(novoContato));
                            }
                        }
                    } else {
                        System.out.println(
                                "Sub pessoa: " + field.get(pessoa) + " para: " + field.get(novaPessoa));
                        field.set(pessoa, field.get(novaPessoa));
                    }

                }

                if (field.get(novaPessoa) != null && field.getName() == "id"
                        && !novaPessoa.getId().toString().equals(pessoa.getId().toString())) {

                    System.out.println("Id não pode ser alterado!");

                } else if (field.get(novaPessoa) != null && field.getName() == "cpf"
                        && !novaPessoa.getCpf().toString().equals(pessoa.getCpf().toString())) {

                    System.out.println("CPF não pode ser alterado!");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return usuarioRepository.save(pessoa);
    }
}
