package com.playpiece.repositories.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.Models.pedido.ItemPedidoModel;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoModel, Long> {
}
