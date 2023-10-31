package com.playpiece.PlayPiece.services;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.models.carrinho.CarrinhoModel;
import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;
import com.playpiece.PlayPiece.repositories.CarrinhoRepository;

@Service
public class CarrinhoService {
    final CarrinhoRepository carrinhoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository) {
        this.carrinhoRepository = carrinhoRepository;
    }

    public CarrinhoModel getCarrinhoByClienteId(Long idCliente) {
        try {
            var carrinho = carrinhoRepository.findByClienteId(idCliente);
            return carrinho;
        } catch (Exception e) {

            return null;
        }
    }

    public CarrinhoModel addItemCarrinho(Long idCarrinho, ItemCarrinhoModel item) {

        try {
            var carrinho = carrinhoRepository.findById(idCarrinho).get();
            var listaItens = carrinho.getItens();
            boolean existente = false;
            for (ItemCarrinhoModel itemCarrinho : listaItens) {
                if (itemCarrinho.getId() == item.getId()) {
                    existente = true;
                    int novaQuantidade = itemCarrinho.getQuantidade() + item.getQuantidade();
                    if (novaQuantidade <= item.getProduto().getQuantidade()) {
                        itemCarrinho.setQuantidade(novaQuantidade);
                        break;
                    } else {
                        break;
                    }
                }
            }
            if (!existente) {
                if (item.getQuantidade() > item.getProduto().getQuantidade()) {
                    throw new Exception();
                } else {
                    listaItens.add(item);
                }
            }
            return carrinhoRepository.save(carrinho);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    // public CarrinhoModel removeItemCarrinho(Long idCarrinho, Long idItem){

    // }
}
