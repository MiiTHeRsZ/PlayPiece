package com.playpieceAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpieceAPI.models.EnderecoModel;
import com.playpieceAPI.repositories.EnderecoRepository;

@Service
public class EnderecoService {

    final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<EnderecoModel> getEnderecoList() {
        try {
            List<EnderecoModel> listaEnderecos = enderecoRepository.findAll();
            return listaEnderecos;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<EnderecoModel>();
        }
    }

    public EnderecoModel getEnderecoById(Long id) {
        try {
            EnderecoModel endereco = enderecoRepository.findById(id).get();
            return endereco;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public EnderecoModel updatePadraoEndereco(Long id) {
        try {
            EnderecoModel endereco = enderecoRepository.findById(id).get();
            endereco.setPadrao(!endereco.isPadrao());
            if (endereco.isPadrao()) {
                List<EnderecoModel> enderecoList = enderecoRepository.findAll();
                for (EnderecoModel enderecoModel : enderecoList) {
                    if (enderecoModel.getId() != endereco.getId()) {
                        if (enderecoModel.isPadrao() && enderecoModel.getIdCliente() == endereco.getIdCliente()) {
                            enderecoModel.setPadrao(false);
                        }
                    }
                }
            }
            endereco = enderecoRepository.save(endereco);
            return endereco;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public EnderecoModel postEndereco(Long idCliente, EnderecoModel novoEndereco) {
        novoEndereco.setId(null);
        novoEndereco.setAtivo(true);
        novoEndereco.setIdCliente(idCliente);
        novoEndereco.setPadrao(false);

        return enderecoRepository.save(novoEndereco);
    }

    public EnderecoModel statusEndereco(Long id) {
        EnderecoModel endereco = enderecoRepository.findById(id).get();
        endereco.setAtivo(!endereco.isAtivo());
        if (endereco.isPadrao()) {
            endereco.setPadrao(false);
        }

        return enderecoRepository.save(endereco);
    }

}
