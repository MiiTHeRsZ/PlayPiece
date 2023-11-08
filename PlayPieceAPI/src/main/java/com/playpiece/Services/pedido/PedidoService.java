package com.playpiece.Services.pedido;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.playpiece.Models.ClienteModel;
import com.playpiece.Models.carrinho.CarrinhoModel;
import com.playpiece.Models.carrinho.ItemCarrinhoModel;
import com.playpiece.Models.pedido.ItemPedidoModel;
import com.playpiece.Models.pedido.PedidoModel;
import com.playpiece.repositories.ClienteRespository;
import com.playpiece.repositories.pedido.PedidoRepository;
import com.playpiece.Services.carrinho.CarrinhoService;

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
