package com.playpiece.playpiece.Services;

import com.playpiece.playpiece.Models.ContatoModel;
import com.playpiece.playpiece.Models.PessoaModel;
import com.playpiece.playpiece.repositories.ContatoRepository;
import com.playpiece.playpiece.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {
    final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

    public List<ContatoModel> getContatos(){
        return contatoRepository.findAll();
    }
}
