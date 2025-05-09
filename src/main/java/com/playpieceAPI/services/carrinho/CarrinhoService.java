package com.playpieceAPI.services.carrinho;

import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.repositories.carrinho.CarrinhoRepository;
import com.playpieceAPI.services.ClienteService;
import com.playpieceAPI.services.ProdutoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class CarrinhoService {

    final CarrinhoRepository carrinhoRepository;
    final ItemCarrinhoService itemCarrinhoService;
    final ProdutoService produtoService;
    final ClienteService clienteService;

    public CarrinhoService(@Lazy CarrinhoRepository carrinhoRepository, @Lazy ItemCarrinhoService itemCarrinhoService,
            @Lazy ProdutoService produtoService, @Lazy ClienteService clienteService) {
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoService = itemCarrinhoService;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    public CarrinhoModel getCarrinhoAtivoByClienteId(Long idCliente) {
        try {
            var carrinho = carrinhoRepository.findByClienteIdAndAtivo(idCliente);
            return carrinho;
        } catch (Exception e) {

            return null;
        }
    }

    public CarrinhoModel addItemCarrinho(Long codCliente, Long codProduto, int quantidade) {

        CarrinhoModel carrinho = null;
        try {
            carrinho = carrinhoRepository.findByClienteIdAndAtivo(codCliente);
            var cliente = carrinho.getCliente();
            var listaItens = carrinho.getItens();
            var novoItem = new ItemCarrinhoModel();

            for (ItemCarrinhoModel item : listaItens) {
                if (item.getProduto().getProdutoId() == codProduto) {
                    var attItem = atualizarQuantidadeItemCarrinho(codCliente, codProduto, quantidade);
                    return attItem;
                }
            }

            try {
                novoItem = itemCarrinhoService.criarItemCarrinho(codProduto, quantidade, cliente.getClienteId());
                novoItem.setCarrinho(carrinho);
                itemCarrinhoService.atualizarItem(novoItem);
                carrinho.getItens().add(novoItem);
                return carrinhoRepository.save(carrinho);
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public CarrinhoModel atualizarQuantidadeItemCarrinho(Long codCliente, Long itemId,
            int novaQuantidade) {
        CarrinhoModel carrinho = carrinhoRepository.findByClienteIdAndAtivo(codCliente);

        var listaItens = carrinho.getItens();

        for (int i = 0; i < listaItens.size(); i++) {
            if (listaItens.get(i).getItemCarrinhoId() == itemId) {
                if (novaQuantidade <= 0) {
                    carrinho.getItens().remove(i);
                } else {
                    try {
                        var produto = produtoService.getProdutoById(listaItens.get(i).getProduto().getProdutoId());
                        if (novaQuantidade <= produto.getQuantidade()) {
                            listaItens.get(i).setQuantidade(novaQuantidade);
                        } else {
                            throw new RuntimeException("Valor acima do disponível em estoque");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Produto não encontrado");
                    }
                }
            }
        }
        return carrinhoRepository.save(carrinho);
    }

    public CarrinhoModel limparCarrinho(CarrinhoModel carrinho) {
        try {
            carrinhoRepository.delByIdCarrinho(carrinho.getCarrinhoId());
            carrinho.setItens(new ArrayList<>());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Falha ao limpar carrinho!");
        }

        return carrinhoRepository.save(carrinho);
    }

    public CarrinhoModel limparCarrinho(Long codCliente) {
        var carrinho = getCarrinhoAtivoByClienteId(codCliente);
        try {
            carrinhoRepository.delByIdCarrinho(carrinho.getCarrinhoId());
            carrinho.setItens(new ArrayList<>());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Falha ao limpar carrinho!");
        }

        return carrinhoRepository.save(carrinho);
    }
}
