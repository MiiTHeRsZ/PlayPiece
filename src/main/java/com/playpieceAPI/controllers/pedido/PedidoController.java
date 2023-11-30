package com.playpieceAPI.controllers.pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playpieceAPI.models.pedido.AtualizarStatusDTO;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.services.pedido.PedidoService;

@RestController
@RequestMapping(value = "pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(value = "search", params = "cliente")
    public ResponseEntity<?> getListaPedidosCliente(@RequestParam Long cliente) {
        try {
            List<PedidoModel> pedidos = new ArrayList<>();
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

        try {
            PedidoModel pedido = new PedidoModel();
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

        try {
            List<PedidoModel> pedidos = new ArrayList<>();
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

        try {
            PedidoModel pedido = new PedidoModel();
            pedido = pedidoService.importarCarrinho(cliente, endereco, frete, modoPagamento);

            return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("update")
    public ResponseEntity<?> atualizarStatus(@RequestBody AtualizarStatusDTO novoPedido) {

        try {
            PedidoModel pedido = new PedidoModel();
            pedido = pedidoService.atualizarStatus(novoPedido);

            return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
