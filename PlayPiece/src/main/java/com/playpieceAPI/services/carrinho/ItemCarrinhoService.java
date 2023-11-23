package com.playpieceAPI.services.carrinho;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.carrinho.ItemCarrinhoRepository;
import com.playpieceAPI.services.ProdutoService;

@Service
public class ItemCarrinhoService {

    final CarrinhoService carrinhoService;
    final ItemCarrinhoRepository itemCarrinhoRepository;
    final ProdutoService produtoService;

    public ItemCarrinhoService(CarrinhoService carrinhoService, ItemCarrinhoRepository itemCarrinhoRepository,
            @Lazy ProdutoService produtoService) {
        this.carrinhoService = carrinhoService;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.produtoService = produtoService;
    }

    public ItemCarrinhoModel getItemCarrinhoById(Long id) {
        ItemCarrinhoModel item = new ItemCarrinhoModel();
        try {
            item = itemCarrinhoRepository.findByItemId(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return item;
    }

    public ItemCarrinhoModel criarItemCarrinho(Long codProduto, int quantidade, Long cliente) {
        ItemCarrinhoModel novoItem = new ItemCarrinhoModel();

        try {
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
            System.out.println(e);
            return null;
        }
    }

    public ItemCarrinhoModel atualizarItem(ItemCarrinhoModel novoItem) {
        try {
            return itemCarrinhoRepository.save(novoItem);
        } catch (Exception e) {
            return null;
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
        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado"));

        if (quantidade <= 0) {
            itemCarrinhoRepository.delete(itemCarrinho);
        } else {
            itemCarrinho.setQuantidade(quantidade);
            itemCarrinhoRepository.save(itemCarrinho);
        }
    }

    // ! Dá uma olhada no CarrinhoService, eu mudei lá
    /*
     * public void excluirItemCarrinho(Long itemCarrinhoId) {
     * try {
     * var itemCarrinho = itemCarrinhoRepository.findById(itemCarrinhoId).get();
     * System.out.println("sem delete");
     * 
     * itemCarrinhoRepository.deleteById(itemCarrinho.getItemCarrinhoId());
     * System.out.println("com delete");
     * } catch (Exception e) {
     * System.out.println(e);
     * }
     * }
     */
}
