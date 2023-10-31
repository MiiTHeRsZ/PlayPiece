package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playpiece.PlayPiece.models.carrinho.ItemCarrinhoModel;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoModel, Long> {

}
