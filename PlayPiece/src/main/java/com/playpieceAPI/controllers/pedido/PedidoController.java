package com.playpieceAPI.controllers.pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.services.pedido.PedidoService;

@RestController
@RequestMapping(value = "pedido")
public class PedidoController {

    final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "search", params = "cliente")
    public ResponseEntity<?> getListaPedidosCliente(@RequestParam Long cliente) {
        List<PedidoModel> pedidos = new ArrayList<>();
        try {
            pedidos = pedidoService.getListaPedidosCliente(cliente);

            return new ResponseEntity<List<PedidoModel>>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "search", params = "id")
    public ResponseEntity<?> getPedidoById(@RequestParam Long id) {
        PedidoModel pedido = new PedidoModel();

        try {
            pedido = pedidoService.getPedidoById(id);

            return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPedidos() {
        List<PedidoModel> pedidos = new ArrayList<>();

        try {
            pedidos = pedidoService.getPedidos();

            return new ResponseEntity<List<PedidoModel>>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "import", params = { "cliente", "endereco", "frete", "modoPagamento" })
    public ResponseEntity<?> importarCarrinho(@RequestParam Long cliente, @RequestParam Long endereco,
            @RequestParam Double frete, @RequestParam String modoPagamento) {
        PedidoModel pedido = new PedidoModel();

        try {
            pedido = pedidoService.importarCarrinho(cliente, endereco, frete, modoPagamento);

            return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
