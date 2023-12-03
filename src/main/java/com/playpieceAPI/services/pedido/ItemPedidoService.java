package com.playpieceAPI.services.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.models.pedido.ItemPedidoModel;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.repositories.ProdutoRepository;
import com.playpieceAPI.repositories.pedido.ItemPedidoRepository;
import com.playpieceAPI.repositories.pedido.PedidoRepository;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public ItemPedidoModel criarItemPedido(ItemCarrinhoModel itemCarrinho, PedidoModel pedido) {
        try {
            ItemPedidoModel itemPedido = new ItemPedidoModel();

            itemPedido.setItemPedidoId(null);
            itemPedido.setProduto(itemCarrinho.getProduto());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setValorUnitario(itemCarrinho.getProduto().getPreco());
            itemPedido.setValorTotal(itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidade());
            itemPedido.setPedido(pedido);

            itemCarrinho.getProduto()
                    .setQuantidade(itemCarrinho.getProduto().getQuantidade() - itemCarrinho.getQuantidade());

            if (itemCarrinho.getProduto().getQuantidade() == 0) {
                itemCarrinho.getProduto().setAtivo(false);
            }
            
            ItemPedidoModel itemSalvo = null;
            produtoRepository.save(itemCarrinho.getProduto());

            itemSalvo = itemPedidoRepository.save(itemPedido);
            return itemSalvo;

        } catch (Exception e) {
            throw e;
        }
    }

    public ItemPedidoModel importarCarrinho(ItemPedidoModel itemPedido) {
        try {
            itemPedido.setItemPedidoId(null);
            itemPedido.setProduto(produtoRepository.findById(itemPedido.getProduto().getProdutoId()).orElse(null));
            itemPedido.setPedido(pedidoRepository.findById(itemPedido.getPedido().getPedidoId()).orElse(null));

            return itemPedidoRepository.save(itemPedido);
        } catch (Exception e) {
            throw e;
        }
    }

}
