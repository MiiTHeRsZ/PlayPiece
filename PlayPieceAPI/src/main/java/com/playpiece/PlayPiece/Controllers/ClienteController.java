package com.playpiece.PlayPiece.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.PlayPiece.Models.ClienteModel;
import com.playpiece.PlayPiece.Models.LoginDto;
import com.playpiece.PlayPiece.Services.ClienteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cliente")
public class ClienteController {

    final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        ClienteModel cliente = clienteService.getClienteById(id);

        if (cliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "search", params = "cpf")
    public ResponseEntity<?> getClienteById(@RequestParam String cpf) {
        ClienteModel cliente = clienteService.getClienteByCpf(cpf);
        if (cliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "search", params = "email")
    public ResponseEntity<?> getClienteEmail(@RequestParam String email) {
        ClienteModel cliente = clienteService.getClienteByEmail(email);

        if (cliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
    }

    @PutMapping("/login")
    public ResponseEntity<?> getClienteLogin(@RequestBody LoginDto login) {
        ClienteModel cliente = clienteService.getClienteLogin(login);

        if (cliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                    HttpStatus.NOT_FOUND);

        return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "login", params = { "senha" })
    public ResponseEntity<?> trocasenha(@RequestParam String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        var novaSenha = encoder.encode(senha);
        return new ResponseEntity<String>(novaSenha, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody ClienteModel cliente) {
        ClienteModel novoCliente = clienteService.postClient(cliente);
        if (novoCliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Falha ao criar cliente\",\n\"code\":" + HttpStatus.BAD_REQUEST.value() + "}",
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ClienteModel>(novoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClienteModel cliente) {

        ClienteModel novoCliente = clienteService.updateCliente(id, cliente);

        if (novoCliente == null)
            return new ResponseEntity<String>(
                    "{\"erro\":\"Falha ao atualiar cliente\",\n\"code\":" + HttpStatus.BAD_REQUEST.value() + "}",
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ClienteModel>(novoCliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> statusCliente(@PathVariable Long id) {
        return new ResponseEntity<ClienteModel>(clienteService.statusCliente(id), HttpStatus.OK);
    }

}
