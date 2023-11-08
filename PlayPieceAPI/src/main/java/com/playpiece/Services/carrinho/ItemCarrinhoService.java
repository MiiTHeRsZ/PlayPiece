package com.playpiece.Services.carrinho;

import org.springframework.stereotype.Service;

import com.playpiece.Models.carrinho.ItemCarrinhoModel;
import com.playpiece.repositories.ProdutoRepository;
import com.playpiece.repositories.carrinho.CarrinhoRepository;
import com.playpiece.repositories.carrinho.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {

    final ItemCarrinhoRepository itemCarrinhoRepository;
    final CarrinhoRepository carrinhoRepository;
    final ProdutoRepository produtoRepository;

    public ItemCarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository, CarrinhoRepository carrinhoRepository,
            ProdutoRepository produtoRepository) {
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ItemCarrinhoModel getItemCarrinhoById(Long id) {
        ItemCarrinhoModel item = new ItemCarrinhoModel();
        try {
            item = itemCarrinhoRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e);
        }
        return item;
    }

    public ItemCarrinhoModel criarItemCarrinho(Long codProduto, int quantidade) {
        ItemCarrinhoModel novoItem = new ItemCarrinhoModel();
        try {
            novoItem.setId(null);
            novoItem.setProduto(produtoRepository.findById(codProduto).get());
            novoItem.setQuantidade(quantidade);
            novoItem.setCarrinho(null);
        } catch (Exception e) {
            System.out.println(e);
        }
        return itemCarrinhoRepository.save(novoItem);
    }

    public ItemCarrinhoModel atualizarItem(ItemCarrinhoModel novoItem) {
        try {
            return itemCarrinhoRepository.save(novoItem);
        } catch (Exception e) {
            return null;
        }
    }

    public ItemCarrinhoModel adicionarItemAoCarrinho(ItemCarrinhoModel itemCarrinho) {
        ItemCarrinhoModel itemExistente = itemCarrinhoRepository
                .findByCarrinhoIdAndProdutoId(itemCarrinho.getCarrinho().getId(), itemCarrinho.getProduto().getId());

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + itemCarrinho.getQuantidade());

            return itemCarrinhoRepository.save(itemExistente);
        } else {
            itemCarrinho.setId(null);
            itemCarrinho.setProduto(produtoRepository.findById(itemCarrinho.getProduto().getId()).orElse(null));
            itemCarrinho.setCarrinho(carrinhoRepository.findById(itemCarrinho.getCarrinho().getId()).orElse(null));
            return itemCarrinhoRepository.save(itemCarrinho);
        }
    }

    public void atualizarQuantidadeItemCarrinho(Long idCarrinho, Long itemId, int quantidade) {
        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado"));

        if (quantidade <= 0) {
            itemCarrinhoRepository.delete(itemCarrinho);
        } else {
            itemCarrinho.setQuantidade(quantidade);
            itemCarrinhoRepository.save(itemCarrinho);
        }
    }
}
