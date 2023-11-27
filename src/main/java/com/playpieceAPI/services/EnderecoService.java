package com.playpieceAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.EnderecoModel;
import com.playpieceAPI.repositories.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteService clienteService;

    public List<EnderecoModel> getEnderecoList() {
        try {
            List<EnderecoModel> listaEnderecos = enderecoRepository.findAll();
            return listaEnderecos;
        } catch (Exception e) {
            throw e;
        }
    }

    public EnderecoModel getEnderecoById(Long id) {
        try {
            EnderecoModel endereco = enderecoRepository.findById(id).get();
            return endereco;
        } catch (Exception e) {
            throw e;
        }
    }

    public EnderecoModel updatePadraoEndereco(Long id) {
        try {
            EnderecoModel endereco = enderecoRepository.findById(id).get();
            endereco.setPadrao(!endereco.isPadrao());
            if (endereco.isPadrao()) {
                List<EnderecoModel> enderecoList = enderecoRepository.findAll();
                for (EnderecoModel enderecoModel : enderecoList) {
                    if (enderecoModel.getEnderecoId() != endereco.getEnderecoId()) {
                        if (enderecoModel.isPadrao()
                                && enderecoModel.getCliente().getClienteId() == endereco.getCliente().getClienteId()) {
                            enderecoModel.setPadrao(false);
                        }
                    }
                }
            }
            endereco = enderecoRepository.save(endereco);
            return endereco;
        } catch (Exception e) {
            throw e;
        }
    }

    public EnderecoModel postEndereco(Long idCliente, EnderecoModel novoEndereco) {
        try {
            ClienteModel cliente = clienteService.getClienteById(idCliente);

            novoEndereco.setEnderecoId(null);
            novoEndereco.setAtivo(true);
            novoEndereco.setCliente(cliente);
            novoEndereco.setPadrao(false);

            return enderecoRepository.save(novoEndereco);
        } catch (Exception e) {
            throw e;
        }

    }

    public EnderecoModel statusEndereco(Long id) {
        try {
            EnderecoModel endereco = enderecoRepository.findById(id).get();
            endereco.setAtivo(!endereco.isAtivo());
            if (endereco.isPadrao()) {
                endereco.setPadrao(false);
            }
            return enderecoRepository.save(endereco);
        } catch (Exception e) {
            throw e;
        }
    }

}
