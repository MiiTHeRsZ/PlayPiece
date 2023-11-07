package com.playpiece.PlayPiece.services.pedido;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.models.ClienteModel;
import com.playpiece.PlayPiece.models.carrinho.CarrinhoModel;
import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;
import com.playpiece.PlayPiece.models.pedido.ItemPedidoModel;
import com.playpiece.PlayPiece.models.pedido.PedidoModel;
import com.playpiece.PlayPiece.repositories.ClienteRespository;
import com.playpiece.PlayPiece.repositories.pedido.PedidoRepository;
import com.playpiece.PlayPiece.services.ClienteService;
import com.playpiece.PlayPiece.services.carrinho.CarrinhoService;

@Service
public class PedidoService {

    final PedidoRepository pedidoRepository;
    final ItemPedidoService itemPedidoService;
    final CarrinhoService carrinhoService;
    final ClienteRespository clienteRespository;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoService itemPedidoService,
            CarrinhoService carrinhoService, ClienteRespository clienteRespository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoService = itemPedidoService;
        this.carrinhoService = carrinhoService;
        this.clienteRespository = clienteRespository;

    }

    public PedidoModel importarCarrinho(Long cliente) {
        PedidoModel pedido = new PedidoModel();
        pedido = pedidoRepository.save(pedido);
        CarrinhoModel carrinho = carrinhoService.getCarrinhoAtivoByClienteId(cliente);
        var listaItens = carrinho.getItens();
        try {

            for (ItemCarrinhoModel itemCarrinho : listaItens) {
                var itemPedido = itemPedidoService.criarItemPedido(itemCarrinho, pedido);
                pedido.setItens(new ArrayList<ItemPedidoModel>());
                pedido.getItens().add(itemPedido);
            }

            carrinhoService.desativarCarrinho(carrinho);

        } catch (Exception e) {
            System.out.println(e);
        }

        ClienteModel novoCliente = carrinho.getCliente();
        if (novoCliente.getPedidos() == null) {
            novoCliente.setPedidos(new ArrayList<PedidoModel>());
        }
        novoCliente.getPedidos().add(pedido);
        pedido.setCliente(novoCliente);

        return pedidoRepository.save(pedido);
    }

}
