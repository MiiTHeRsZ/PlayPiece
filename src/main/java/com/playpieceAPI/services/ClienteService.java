package com.playpieceAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.EnderecoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRespository clienteRespository;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public ClienteModel getClienteById(Long id) {
        try {
            ClienteModel cliente = clienteRespository.findById(id).get();
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel getClienteByCpf(String cpf) {
        try {
            ClienteModel cliente = clienteRespository.findByCpf(cpf);
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel getClienteByEmail(String email) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(email);
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel getClienteLogin(LoginDto login) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(login.getEmail());
            if (cliente != null) {
                var result = encoder.matches(login.getSenha(), cliente.getSenha());
                if (result && login.getEmail().equalsIgnoreCase(cliente.getEmail())) {
                    return cliente;
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel postClient(ClienteModel cliente) {
        try {
            var senhaCripto = encoder.encode(cliente.getSenha());
            cliente.setClienteId(null);
            cliente.setAtivo(true);
            cliente.setSenha(senhaCripto);
            if (!cliente.getListaEndereco().isEmpty()) {
                enderecoService.postEndereco(0L, cliente.getListaEndereco().get(0),
                        cliente.getListaEndereco().get(0).isPadrao());
            }
            enderecoService.postEndereco(0L, cliente.getEnderecoFaturamento(),
                    cliente.getEnderecoFaturamento().isPadrao());
            cliente = clienteRespository.save(cliente);
            var endereco = cliente.getEnderecoFaturamento();
            endereco.setCliente(cliente);
            enderecoRepository.save(endereco);

            if (!cliente.getListaEndereco().isEmpty()) {
                cliente.getListaEndereco().get(0).setCliente(cliente);
                enderecoRepository.save(cliente.getListaEndereco().get(0));
            }
            cliente = clienteRespository.save(cliente);

            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel updateCliente(Long id, ClienteModel novoCliente) {
        try {
            ClienteModel cliente = clienteRespository.findById(id).get();
            var res = encoder.matches(novoCliente.getSenha(), cliente.getSenha());
            if (!res) {
                var senhaCripto = encoder.encode(novoCliente.getSenha());
                novoCliente.setSenha(senhaCripto);
            }
            novoCliente.setClienteId(cliente.getClienteId());
            novoCliente.setEmail(cliente.getEmail());
            novoCliente.setCpf(cliente.getCpf());

            cliente = novoCliente;

            cliente.setEnderecoFaturamento(
                    enderecoService.getEnderecoById(cliente.getEnderecoFaturamento().getEnderecoId()));

            cliente = clienteRespository.save(cliente);

            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    public ClienteModel statusCliente(Long id) {
        try {
            ClienteModel cliente = clienteRespository.findById(id).get();
            cliente.setAtivo(!cliente.getAtivo());
            cliente.setListaEndereco(null);

            return clienteRespository.save(cliente);
        } catch (Exception e) {
            throw e;
        }
    }
}
