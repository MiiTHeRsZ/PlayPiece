package com.playpieceAPI.repositories.carrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoModel, Long> {

    @Query("SELECT i FROM item_carrinho i WHERE i.carrinho.id = ?1 AND i.produto.id = ?2")
    ItemCarrinhoModel findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);

    @Query(nativeQuery = true, value = "select * from item_carrinho i inner join carrinho c on c.carrinho_id = i.fk_carrinho_id inner join cliente p on p.cliente_id = c.fk_cliente_id where i.item_carrinho_id = ?1")
    ItemCarrinhoModel findByItemId(Long id);

}
