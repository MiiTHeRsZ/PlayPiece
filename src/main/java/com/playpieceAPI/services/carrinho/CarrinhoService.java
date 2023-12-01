package com.playpieceAPI.services.carrinho;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.repositories.carrinho.CarrinhoRepository;
import com.playpieceAPI.services.ProdutoService;

@Service

public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ItemCarrinhoService itemCarrinhoService;
    @Autowired
    private ProdutoService produtoService;

    public CarrinhoModel getCarrinhoAtivoByClienteId(Long idCliente) {
        try {
            var carrinho = carrinhoRepository.findByClienteIdAndAtivo(idCliente);
            return carrinho;
        } catch (Exception e) {

            throw e;
        }
    }

    public CarrinhoModel addItemCarrinho(Long codCliente, Long codProduto, int quantidade) {

        try {
            CarrinhoModel carrinho = null;
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
                throw e;
            }

        } catch (Exception e) {
            throw e;
        }

    }

    public CarrinhoModel atualizarQuantidadeItemCarrinho(Long codCliente, Long itemId,
            int novaQuantidade) {
        try {
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
        } catch (Exception e) {
            throw e;
        }
    }

    public CarrinhoModel limparCarrinho(CarrinhoModel carrinho) {
        try {
            carrinhoRepository.delByIdCarrinho(carrinho.getCarrinhoId());
            carrinho.setItens(new ArrayList<>());
            return carrinhoRepository.save(carrinho);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao limpar carrinho!");
        }

    }

    public CarrinhoModel limparCarrinho(Long codCliente) {
        var carrinho = getCarrinhoAtivoByClienteId(codCliente);

        if (!carrinho.getItens().isEmpty()) {
            try {
                carrinhoRepository.delByIdCarrinho(carrinho.getCarrinhoId());
                carrinho.setItens(new ArrayList<>());
                return carrinhoRepository.save(carrinho);
            } catch (Exception e) {
                throw new RuntimeException("Falha ao limpar carrinho!");
            }
        }

        return carrinho;
    }
}
