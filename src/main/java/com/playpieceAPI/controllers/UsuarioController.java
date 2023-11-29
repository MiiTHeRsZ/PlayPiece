package com.playpieceAPI.controllers;

import java.util.ArrayList;
import java.util.List;

import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/usuario")
public class UsuarioController {

    /***
     * Atributo vindo da classe UsuarioService. Todas as funções/ metodos chamadas, 
     * serão sobrescritos pro conta da notação @Autowired
     */
    @Autowired
    private UsuarioService usuarioService;

    /***
     * @return método de requisição HTTP tipo GET, responsavel por retornar uma 
     *         lista com todos usuarios cadastrados no backoffice do sistema. 
     *         Endereço: .../usuario
     * @Observation Para retornar a lista dos usuarios, ele chama método 
     *              getUsuarioList, vindo da classe UsuarioService
     */
    @GetMapping
    public ResponseEntity<?> getUsuarioList() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        try {
            usuarios = usuarioService.getUsuarioList();
            // verifica se a lista retorna vazio, caso sim, acaba lançando uma exceção
            if (usuarios.isEmpty())
                return new ResponseEntity<>("Não há usuários", HttpStatus.NOT_FOUND);

            return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @return método de requisição HTTP tipo GET, responsavel por retornar um 
     *         usuario cadastrados no backoffice do sistema atraves do seu ID. 
     *         Endereço: .../usuario/{ID_pesquisado}
     * @Observation Para verificar se o ID pesquisado existe, ele chama método 
     *              getUsuarioById, vindo da classe UsuarioService
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            var usuario = usuarioService.getUsuarioById(id);
            // verifica se o usuario pesquisado existe, caso contrario retorna vazio, lançando uma exceção
            if (usuario == null)
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(usuario, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @return método de requisição HTTP tipo PUT, responsavel por verificar se o 
     *         usuario que efetuou o login existe e 
     *         logo em seguida alterar dados (email, senha, cargo).
     *         Endereço: .../login
     * @Observation Para verificar se o usuario já existe, ele chama método 
     *              usuarioLogin, vindo da classe UsuarioService
     */
    @PutMapping("/login")
    public ResponseEntity<?> getUsuarioByEmail(@RequestBody LoginDto login) {

        UsuarioModel usuario;

        try {
            usuario = usuarioService.usuarioLogin(login);
            if (usuario == null)
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);

            List<String> resuList = new ArrayList<>();
            resuList.add(usuario.getUsuarioId().toString());
            resuList.add(usuario.getEmailUsuario());
            resuList.add(usuario.getSenha());
            resuList.add(usuario.getCargo().getCargoId().toString());

            return new ResponseEntity<>(resuList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @return método de requisição HTTP tipo GET, responsavel por pesquisar o nome 
     *         de um usuario cadastrado no sistema. 
     *         Caso ele ache algum usuario que contenha o "texto" que foi 
     *         pesquisado, ele retorna uma lista de usuarios, 
     *         caso contrario, ele lança uma exceção. 
     *         Endereço: .../?
     * @Observation Para verificar se algum usuario possui o nome (trecho) 
     *              pesquisado, ele chama método getUsuarioByNome, vindo da classe UsuarioService.
     */
    @GetMapping(value = "search", params = { "nome" })
    public ResponseEntity<?> getUsuarioByNome(@RequestParam String nome) {
        try {
            List<UsuarioModel> usuarios = usuarioService.getUsuarioByNome(nome);
            if (usuarios.isEmpty())
                return new ResponseEntity<>("Usuários não encontrados", HttpStatus.NOT_FOUND);

            return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @return método de requisição HTTP tipo POST, responsavel por enviar dados 
     *         recebidos do usuario 
     *         e efetuar um novo cadastro no sistema backoffice.
     * @Observation Para salvar os dados do usuario, ele chama o método postUsuario, 
     *              vindo da classe UsuarioService 
     *              Endereço: .../usuario
     */
    @PostMapping
    public ResponseEntity<?> postUsuario(@RequestBody UsuarioModel usuario) {
        try {
            UsuarioModel novoUsuario = usuarioService.postUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return método de requisição HTTP tipo PUT responsavel por realizar 
     *         alterações nos dados do usuario 
     *         (senha, email e cargo) e salva-las de maneira Transactional no banco 
     *          Endereço: .../{ID_desejado}
     * @Observation Para salvar as alterações dos dados do usuario, ele chama o 
     *              método updateUsuario, vindo da classe UsuarioService 
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id,
            @RequestBody UsuarioModel novoUsuario) {

        try {
            return new ResponseEntity<>(usuarioService.updateUsuario(id, novoUsuario),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return método de requisição HTTP tipo DELETE, responsavel por realizar 
     *         alteração no status do usuario 
     *         atraves da pesquisa do seu ID. Quando o método é chamado, ele altera 
     *         o valor do status para o oposto. 
     *         Ativo se torna inativo, assim como inativo se torna ativo 
     *         Endereço: .../{ID_desejado}
     * @Observation Para realizar essas alterações as alterações no status do
     *              usuario, ele chama o método statusUsuario, vindo da classe
     *              UsuarioService
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> statusUsuario(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(usuarioService.statusUsuario(id),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
