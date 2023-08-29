package com.playpiece.PlayPiece.Services;

import com.playpiece.PlayPiece.Models.*;
import com.playpiece.PlayPiece.repositories.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ContatoService {
    final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public List<ContatoModel> getContatos() {
        return contatoRepository.findAll();
    }

    public ContatoModel patchContato(int id, ContatoModel novoContato) {

        ContatoModel contato = contatoRepository.findById(id).get();
        try {
            for (Field contatoField : ContatoModel.class.getDeclaredFields()) {
                contatoField.setAccessible(true);

                if (contatoField.get(novoContato) != null
                        && !contatoField.get(novoContato).equals(contatoField.get(contato))
                        && contatoField.getName() != "id") {

                    contatoField.set(contato, contatoField.get(novoContato));
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return contato;
    }
}
