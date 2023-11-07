package com.playpiece.PlayPiece.services.carrinho;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.models.ClienteModel;
import com.playpiece.PlayPiece.models.carrinho.CarrinhoModel;
import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;
import com.playpiece.PlayPiece.repositories.carrinho.CarrinhoRepository;
import com.playpiece.PlayPiece.services.ClienteService;
import com.playpiece.PlayPiece.services.ProdutoService;

@Service
public class CarrinhoService {

    final CarrinhoRepository carrinhoRepository;
    final ItemCarrinhoService itemCarrinhoService;
    final ProdutoService produtoService;
    final ClienteService clienteService;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ItemCarrinhoService itemCarrinhoService,
            ProdutoService produtoService, ClienteService clienteService) {
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoService = itemCarrinhoService;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    public CarrinhoModel getCarrinhoAtivoByClienteId(Long idCliente) {
        try {
            var carrinho = carrinhoRepository.findByClienteIdAndAtivo(idCliente);

            if (carrinho == null) {
                carrinho = criarCarrinho(idCliente);
            }

            return carrinho;
        } catch (Exception e) {

            return null;
        }
    }

    public CarrinhoModel criarCarrinho(Long codCliente) {
        CarrinhoModel carrinho = new CarrinhoModel();
        ClienteModel cliente = clienteService.getClienteById(codCliente);

        carrinho.setCliente(cliente);
        carrinho.setAtivo(true);

        return carrinhoRepository.save(carrinho);
    }

    public CarrinhoModel addItemCarrinho(Long codCarrinho, Long codProduto, int quantidade) {

        CarrinhoModel carrinho = null;
        try {
            carrinho = carrinhoRepository.findById(codCarrinho).get();
            var listaItens = carrinho.getItens();
            var novoItem = new ItemCarrinhoModel();

            for (ItemCarrinhoModel item : listaItens) {
                if (item.getId() == codProduto) {
                    var attItem = atualizarQuantidadeItemCarrinho(codCarrinho, codProduto, quantidade);
                    return attItem;
                }
            }

            try {
                novoItem = itemCarrinhoService.criarItemCarrinho(codProduto, quantidade);
                novoItem.setCarrinho(carrinho);
                novoItem = itemCarrinhoService.atualizarItem(novoItem);
                carrinho.getItens().add(novoItem);
            } catch (Exception e) {
                System.out.println(e);
            }

            return carrinhoRepository.save(carrinho);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public CarrinhoModel atualizarQuantidadeItemCarrinho(Long carrinhoId, Long itemId,
            int novaQuantidade) {
        CarrinhoModel carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        var listaItens = carrinho.getItens();

        for (int i = 0; i < listaItens.size(); i++) {
            if (listaItens.get(i).getId() == itemId) {
                if (novaQuantidade <= 0) {
                    carrinho.getItens().remove(i);
                } else {
                    try {
                        var produto = produtoService.getProdutoById(listaItens.get(i).getProduto().getId());
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
        itemCarrinhoService.atualizarQuantidadeItemCarrinho(carrinhoId, itemId, novaQuantidade);

        return carrinhoRepository.save(carrinho);
    }

    public CarrinhoModel desativarCarrinho(CarrinhoModel carrinho) {
        carrinho.setAtivo(false);

        return carrinhoRepository.save(carrinho);
    }
}
