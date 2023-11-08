package com.playpieceAPI.services.pedido;

import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.models.pedido.ItemPedidoModel;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.repositories.ProdutoRepository;
import com.playpieceAPI.repositories.pedido.ItemPedidoRepository;
import com.playpieceAPI.repositories.pedido.PedidoRepository;

@Service
public class ItemPedidoService {

    final ItemPedidoRepository itemPedidoRepository;
    final ProdutoRepository produtoRepository;
    final PedidoRepository pedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository,
            PedidoRepository pedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public ItemPedidoModel criarItemPedido(ItemCarrinhoModel itemCarrinho, PedidoModel pedido) {
        ItemPedidoModel itemPedido = new ItemPedidoModel();

        itemPedido.setId(null);
        itemPedido.setProduto(itemCarrinho.getProduto());
        itemPedido.setQuantidade(itemCarrinho.getQuantidade());
        itemPedido.setValorUnitario(itemCarrinho.getProduto().getPreco());
        itemPedido.setValorTotal(itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidade());
        itemPedido.setPedido(pedido);

        ItemPedidoModel itemSalvo = null;

        try {
            itemSalvo = itemPedidoRepository.save(itemPedido);
        } catch (Exception e) {
            System.out.println(e);
        }

        return itemSalvo;
    }

    public ItemPedidoModel importarCarrinho(ItemPedidoModel itemPedido) {
        try {
            itemPedido.setId(null);
            itemPedido.setProduto(produtoRepository.findById(itemPedido.getProduto().getId()).orElse(null));
            itemPedido.setPedido(pedidoRepository.findById(itemPedido.getPedido().getId()).orElse(null));

        } catch (Exception e) {
            System.out.println(e);
        }
        return itemPedidoRepository.save(itemPedido);
    }

}
