package com.playpieceAPI.services.carrinho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.repositories.carrinho.ItemCarrinhoRepository;
import com.playpieceAPI.services.ProdutoService;

@Service
public class ItemCarrinhoService {

    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;
    @Autowired
    private ProdutoService produtoService;

    public ItemCarrinhoModel getItemCarrinhoById(Long id) {
        try {
            ItemCarrinhoModel item = new ItemCarrinhoModel();
            item = itemCarrinhoRepository.findByItemId(id);
            return item;
        } catch (Exception e) {
            throw e;
        }
    }

    public ItemCarrinhoModel criarItemCarrinho(Long codProduto, int quantidade, Long cliente) {

        try {
            ItemCarrinhoModel novoItem = new ItemCarrinhoModel();
            novoItem.setItemCarrinhoId(null);
            var produto = produtoService.getProdutoById(codProduto);
            novoItem.setProduto(produto);

            if (quantidade > produto.getQuantidade())
                throw new RuntimeException("Quantidade maior que estoque");
            else
                novoItem.setQuantidade(quantidade);

            var carrinho = carrinhoService.getCarrinhoAtivoByClienteId(cliente);
            novoItem.setCarrinho(carrinho);
            return itemCarrinhoRepository.save(novoItem);

        } catch (Exception e) {
            throw e;
        }
    }

    public ItemCarrinhoModel atualizarItem(ItemCarrinhoModel novoItem) {
        try {
            return itemCarrinhoRepository.save(novoItem);
        } catch (Exception e) {
            throw e;
        }
    }

    // public ItemCarrinhoModel adicionarItemAoCarrinho(ItemCarrinhoModel
    // itemCarrinho, Long clienteId) {

    // CarrinhoModel carrinho =
    // carrinhoService.getCarrinhoAtivoByClienteId(clienteId);
    // ItemCarrinhoModel itemExistente = itemCarrinhoRepository
    // .findByCarrinhoIdAndProdutoId(carrinho.getId(),
    // itemCarrinho.getProduto().getId());

    // if (itemExistente != null) {
    // itemExistente.setQuantidade(itemExistente.getQuantidade() +
    // itemCarrinho.getQuantidade());

    // return itemCarrinhoRepository.save(itemExistente);
    // } else {
    // ItemCarrinhoModel novoItem = new ItemCarrinhoModel();
    // novoItem.setId(null);
    // var produto =
    // produtoService.getProdutoById(itemCarrinho.getProduto().getId());
    // novoItem.setProduto(produto);
    // novoItem.setCarrinho(carrinho);
    // return itemCarrinhoRepository.save(itemCarrinho);
    // }
    // }

    public void atualizarQuantidadeItemCarrinho(Long idCarrinho, Long itemId, int quantidade) {
        try {
            ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado"));

            if (quantidade <= 0) {
                itemCarrinhoRepository.delete(itemCarrinho);
            } else {
                itemCarrinho.setQuantidade(quantidade);
                itemCarrinhoRepository.save(itemCarrinho);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
