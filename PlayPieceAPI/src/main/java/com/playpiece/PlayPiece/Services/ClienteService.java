package com.playpiece.PlayPiece.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.ClienteModel;
import com.playpiece.PlayPiece.Models.EnderecoModel;
import com.playpiece.PlayPiece.repositories.ClienteRespository;
import com.playpiece.PlayPiece.repositories.EnderecoRepository;

@Service
public class ClienteService {

    final ClienteRespository clienteRespository;
    final EnderecoService enderecoService;
    final EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRespository clienteRespository, EnderecoService enderecoService,
            EnderecoRepository enderecoRepository) {
        this.clienteRespository = clienteRespository;
        this.enderecoService = enderecoService;
        this.enderecoRepository = enderecoRepository;
    }

    public ClienteModel getClienteById(Long id) {
        try {
            ClienteModel cliente = clienteRespository.findById(id).get();
            cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));
            return cliente;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ClienteModel getClienteByCpf(String cpf) {
        try {
            ClienteModel cliente = clienteRespository.findByCpf(cpf);
            cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));
            return cliente;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ClienteModel getClienteByEmail(String email) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(email);
            cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));
            return cliente;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ClienteModel postClient(ClienteModel cliente) {
        cliente.setId(null);
        cliente.setAtivo(true);
        enderecoService.postEndereco(0L, cliente.getEnderecoFaturamento());
        cliente = clienteRespository.save(cliente);
        var endFat = enderecoRepository.save(cliente.getEnderecoFaturamento());
        cliente.setEnderecoFaturamento(enderecoService.getEnderecoById(endFat.getId()));
        cliente.getEnderecoFaturamento().setIdCliente(cliente.getId());

        cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));

        return cliente;
    }

    public ClienteModel updateCliente(Long id, ClienteModel novoCliente) {
        ClienteModel cliente = clienteRespository.findById(id).get();
        novoCliente.setId(cliente.getId());
        novoCliente.setEmail(cliente.getEmail());
        novoCliente.setCpf(cliente.getCpf());

        cliente = novoCliente;

        cliente.setEnderecoFaturamento(enderecoService.getEnderecoById(cliente.getEnderecoFaturamento().getId()));

        cliente = clienteRespository.save(cliente);

        cliente.setListaEndereco(adicionarEnderecosCliente(id));

        return cliente;
    }

    public ClienteModel statusCliente(Long id) {
        ClienteModel cliente = clienteRespository.findById(id).get();
        cliente.setAtivo(!cliente.getAtivo());
        cliente.setListaEndereco(null);

        return clienteRespository.save(cliente);
    }

    private List<EnderecoModel> adicionarEnderecosCliente(Long id) {
        ClienteModel cliente = clienteRespository.findById(id).get();
        List<EnderecoModel> listaEndereco = enderecoService.getEnderecoList();
        List<EnderecoModel> enderecos = new ArrayList<EnderecoModel>();

        for (EnderecoModel endereco : listaEndereco) {
            if (endereco.getIdCliente() == cliente.getId())
                enderecos.add(endereco);
        }

        if (enderecos.size() == 1) {
            var endPadrao = enderecos.get(0);
            cliente.setEnderecoFaturamento(endPadrao);
            endPadrao.setAtivo(true);
            endPadrao.setPadrao(true);
            clienteRespository.save(cliente);
            enderecoRepository.save(endPadrao);
        }

        return enderecos;

    }
}
