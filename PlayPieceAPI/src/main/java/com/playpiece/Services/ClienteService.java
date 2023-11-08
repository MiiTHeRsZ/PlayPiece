package com.playpiece.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpiece.Models.ClienteModel;
import com.playpiece.Models.EnderecoModel;
import com.playpiece.Models.LoginDto;
import com.playpiece.repositories.ClienteRespository;
import com.playpiece.repositories.EnderecoRepository;

@Service
public class ClienteService {

    final ClienteRespository clienteRespository;
    final EnderecoService enderecoService;
    final EnderecoRepository enderecoRepository;
    final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

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

    public ClienteModel getClienteLogin(LoginDto login) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(login.getEmail());
            cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));
            var result = encoder.matches(login.getSenha(), cliente.getSenha());
            if (result && login.getEmail().equalsIgnoreCase(cliente.getEmail())) {
                return cliente;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ClienteModel postClient(ClienteModel cliente) {
        var senhaCripto = encoder.encode(cliente.getSenha());
        cliente.setId(null);
        cliente.setAtivo(true);
        cliente.setSenha(senhaCripto);
        enderecoService.postEndereco(0L, cliente.getEnderecoFaturamento());
        cliente = clienteRespository.save(cliente);

        // var carrinho = carrinhoRepository.save(new CarrinhoModel());

        var endFat = enderecoRepository.save(cliente.getEnderecoFaturamento());
        cliente.setEnderecoFaturamento(enderecoService.getEnderecoById(endFat.getId()));
        cliente.getEnderecoFaturamento().setIdCliente(cliente.getId());

        cliente.setListaEndereco(adicionarEnderecosCliente(cliente.getId()));

        return cliente;
    }

    public ClienteModel updateCliente(Long id, ClienteModel novoCliente) {
        ClienteModel cliente = clienteRespository.findById(id).get();
        var res = encoder.matches(novoCliente.getSenha(), cliente.getSenha());
        if (!res) {
            var senhaCripto = encoder.encode(novoCliente.getSenha());
            novoCliente.setSenha(senhaCripto);
        }
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
