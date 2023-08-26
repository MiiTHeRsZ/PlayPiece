package com.playpiece.PlayPiece.Services;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.repositories.*;
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
