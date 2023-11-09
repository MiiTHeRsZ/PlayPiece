package com.playpieceAPI.services.pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpieceAPI.models.ClienteModel;
import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.models.pedido.ItemPedidoModel;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.pedido.PedidoRepository;
import com.playpieceAPI.services.carrinho.CarrinhoService;

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
                if (pedido.getItens() == null) {
                    pedido.setItens(new ArrayList<ItemPedidoModel>());
                }
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

    public List<PedidoModel> getListaPedidosCliente(Long id) {
        List<PedidoModel> pedidos = new ArrayList<>();

        try {
            pedidos = pedidoRepository.findAllByClienteId(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return pedidos;
    }

}
