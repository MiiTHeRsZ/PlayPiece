package com.playpieceAPI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.repositories.CargoRepository;
import com.playpieceAPI.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /***
     * @return método responsavel por retornar uma lista com todos usuarios cadastrados no backoffice do sistema
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface UsuarioRepository
     */
    public List<UsuarioModel> getUsuarioList() {
        List<UsuarioModel> listaUsuario = new ArrayList<>();
        try {
            listaUsuario = usuarioRepository.findAll();
            return listaUsuario;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por retornar um usuario do backoffice do sistema atraves do seu ID \n
     * caso não retorne nada, significa que não encontrou o ID desejado
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface UsuarioRepository
     */    
    public UsuarioModel getUsuarioById(Long id) {
        try {
            UsuarioModel usuario = usuarioRepository.findById(id).orElse(null);
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    /***
     * @return método responsavel por retornar um usuario do backoffice do sistema atraves do seu email, que é unico
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface UsuarioRepository
     */  
    public UsuarioModel getUsuarioByEmail(String email) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(email);
            return usuario;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    /***
     * @return método responsavel por filtrar algum nome e retornar uma lista 
     * com todos usuarios encontrados no backoffice do sistema.
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface UsuarioRepository
     */    
    public List<UsuarioModel> getUsuarioByNome(String nome) {
        List<UsuarioModel> usuarios = new ArrayList<>();
        try {
            usuarios = usuarioRepository.findByNomeContaining(nome);
            return usuarios;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel salvar dados do cadastro do usuario no banco de dados. 
     * Já salva a senha encriptada e set o status do usuario como true por padrão
     * @Observation: Vale ressaltar que ele usa funções vindas das interfaces UsuarioRepository, BCryptPasswordEncoder e CargoRepository. 
     */      
    public UsuarioModel postUsuario(UsuarioModel usuario) {
        try {
            var senhaCripto = encoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCripto);
            usuario.setUsuarioId(null);
            usuario.setCargo(cargoRepository.findById(usuario.getCargo().getCargoId()).get());
            usuario.setAtivo(true);

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por pegar o status do usuario filtrado (filtro atraves do seu ID) e 
     * alterar o seu valor para o oposto
     * @Observation: Vale ressaltar que ele usa uma função vinda da interface UsuarioRepository
     */  
    public UsuarioModel statusUsuario(Long id) {

        try {
            UsuarioModel usuario = usuarioRepository.findById(id).get();
            usuario.setAtivo(!usuario.getAtivo());

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por realizar alterações nos dados do usuario e salvar de maneira Transactional.
     * Dados como: senha, email e  cargo.
     * @Observation: Vale ressaltar que ele usa funções vindas das interfaces UsuarioRepository, BCryptPasswordEncoder e CargoRepository
     */ 
    public UsuarioModel updateUsuario(Long id, UsuarioModel novoUsuario) {
        try {

            UsuarioModel usuario = usuarioRepository.findById(id).get();

            // verifica se a nova senha é igual a senha antiga
            var res = encoder.matches(novoUsuario.getSenha(), usuario.getSenha());
            
            // caso as senhas forem diferentes, ele seta a senha para a nova que o usuario inseriu
            if (!res) {
                var senhaCripto = encoder.encode(novoUsuario.getSenha());
                novoUsuario.setSenha(senhaCripto);
            }

            novoUsuario.setUsuarioId(usuario.getUsuarioId());
            novoUsuario.setEmailUsuario(usuario.getEmailUsuario());
            usuario = novoUsuario;

            usuario.setCargo(cargoRepository.findById(usuario.getCargo().getCargoId()).get());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * @return método responsavel por pesquisar um usuario pelo seu email e depois conferir 
     * se o encript da senha fornecida é o mesmo da senha salva no banco de dados
     * @Observation: Vale ressaltar que ele usa uma função vinda da interfaces UsuarioRepository
     */
    public UsuarioModel usuarioLogin(LoginDto login) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmailUsuario(login.getEmail());
            if (usuario != null) {
                var result = encoder.matches(login.getSenha(), usuario.getSenha());
                if (result && login.getEmail().equalsIgnoreCase(usuario.getEmailUsuario())) {
                    return usuario;
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
}
