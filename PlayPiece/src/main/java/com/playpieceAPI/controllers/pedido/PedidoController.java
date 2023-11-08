package com.playpieceAPI.controllers.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "import", params = "cliente")
    public ResponseEntity<?> importarCarrinho(@RequestParam Long cliente) {
        PedidoModel pedido = new PedidoModel();

        try {
            pedido = pedidoService.importarCarrinho(cliente);

            return new ResponseEntity<PedidoModel>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "{\"erro\":\"" + e.getMessage() + "\",\n\"code\":" + HttpStatus.INTERNAL_SERVER_ERROR.value()
                            + "}",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
