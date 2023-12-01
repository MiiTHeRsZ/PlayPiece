package com.playpieceAPI.services;

import com.playpieceAPI.models.EnderecoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.EnderecoRepository;
import com.playpieceAPI.repositories.carrinho.CarrinhoRepository;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRespository clienteRespository;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /***
     * @return método responsavel por retornar um cliente do sistema atraves do seu
     *         ID \n
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface
     *               ClienteRespository
     */
    public ClienteModel getClienteById(Long id) {
        try {
            ClienteModel cliente = clienteRespository.findById(id).get();
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por retornar um cliente do sistema atraves do seu
     *         CPF \n
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface
     *               ClienteRespository
     */
    public ClienteModel getClienteByCpf(String cpf) {
        try {
            ClienteModel cliente = clienteRespository.findByCpf(cpf);
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por retornar um cliente do sistema atraves do seu
     *         email \n
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface
     *               ClienteRespository
     */
    public ClienteModel getClienteByEmail(String email) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(email);
            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por pesquisar um cliente pelo seu email e depois
     *         conferir
     *         se o encript da senha fornecida é o mesmo da senha salva no banco de
     *         dados.
     *         Caso ambas as verificações sejem verdadeiras (o login e senha
     *         fornecidos conferem com o email e senha cadastrados
     *         no banco de dados), o sistema retorna o cliente.
     *         Caso retorne null, significa que não encontrou o email desejado
     * @Observation: Vale ressaltar que ele usa funções vindas das interfaces
     *               ClienteRespository e BCryptPasswordEncoder
     */
    public ClienteModel getClienteLogin(LoginDto login) {
        try {
            ClienteModel cliente = clienteRespository.findByEmail(login.getEmail());
            if (cliente != null) {
                // verifica se a nova senha é igual a senha antiga
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

    /***
     * @return método responsavel salvar dados do cadastro do cliente no banco de
     *         dados.
     *         Salva a senha encriptada;
     *         Seta o status do clietne como true por padrão;
     *         Verifica se o cliente preencheu algum endereço, caso sim
     * @Observation: Vale ressaltar que ele usa funções vindas das interfaces
     *               UsuarioRepository, BCryptPasswordEncoder e CargoRepository.
     */
    public ClienteModel postClient(ClienteModel cliente) {
        try {
            var senhaCripto = encoder.encode(cliente.getSenha());
            cliente.setClienteId(null);
            cliente.setAtivo(true);
            cliente.setSenha(senhaCripto);
            // verifica se a lista de endereço do cliente não está vazia
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

            carrinhoRepository.save(new CarrinhoModel(null, cliente, null));

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
            novoCliente.getEnderecoFaturamento().setCliente(novoCliente);

            cliente = novoCliente;

            cliente = clienteRespository.save(cliente);

            return cliente;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por pegar o status do usuario filtrado (filtro
     *         atraves do seu ID) e
     *         alterar o seu valor para o oposto, alem de setar como nulo a sua
     *         lista de endereços cadastrados
     * @Observation: Vale ressaltar que ele usa função vinda da interface
     *               ClienteRepository
     */
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
