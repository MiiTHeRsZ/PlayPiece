package com.playpiece.repositories.carrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.playpiece.models.carrinho.ItemCarrinhoModel;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoModel, Long> {

    @Query("SELECT i FROM item_carrinho i WHERE i.carrinho.id = ?1 AND i.produto.id = ?2")
    ItemCarrinhoModel findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);

}
