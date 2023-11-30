package com.playpieceAPI.controllers;

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

import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.services.ClienteService;
import com.playpieceAPI.services.carrinho.CarrinhoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        try {
            ClienteModel cliente = clienteService.getClienteById(id);

            if (cliente == null) {
                return new ResponseEntity<String>(
                        "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "search", params = "cpf")
    public ResponseEntity<?> getClienteById(@RequestParam String cpf) {
        try {
            ClienteModel cliente = clienteService.getClienteByCpf(cpf);
            if (cliente == null)
                return new ResponseEntity<String>(
                        "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                        HttpStatus.NOT_FOUND);

            return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "search", params = "email")
    public ResponseEntity<?> getClienteEmail(@RequestParam String email) {
        try {
            ClienteModel cliente = clienteService.getClienteByEmail(email);

            if (cliente == null)
                return new ResponseEntity<String>(
                        "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                        HttpStatus.NOT_FOUND);

            return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/login")
    public ResponseEntity<?> getClienteLogin(@RequestBody LoginDto login) {
        try {
            ClienteModel cliente = clienteService.getClienteLogin(login);

            if (cliente == null)
                return new ResponseEntity<String>(
                        "{\"erro\":\"Cliente não encontrado\",\n\"code\":" + HttpStatus.NOT_FOUND.value() + "}",
                        HttpStatus.NOT_FOUND);

            else {
                var carrinho = carrinhoService.getCarrinhoAtivoByClienteId(cliente.getClienteId());
                carrinhoService.limparCarrinho(carrinho);
                return new ResponseEntity<ClienteModel>(cliente, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody ClienteModel cliente) {
        try {
            ClienteModel novoCliente = clienteService.postClient(cliente);
            if (novoCliente == null)
                return new ResponseEntity<String>(
                        "{\"erro\":\"Falha ao criar cliente\",\n\"code\":" + HttpStatus.BAD_REQUEST.value() + "}",
                        HttpStatus.BAD_REQUEST);

            return new ResponseEntity<ClienteModel>(novoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClienteModel cliente) {
        try {
            ClienteModel novoCliente = clienteService.updateCliente(id, cliente);

            if (novoCliente == null)
                return new ResponseEntity<String>(
                        "{\"erro\":\"Falha ao atualiar cliente\",\n\"code\":" + HttpStatus.BAD_REQUEST.value() + "}",
                        HttpStatus.BAD_REQUEST);

            return new ResponseEntity<ClienteModel>(novoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> statusCliente(@PathVariable Long id) {
        try {
            return new ResponseEntity<ClienteModel>(clienteService.statusCliente(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
