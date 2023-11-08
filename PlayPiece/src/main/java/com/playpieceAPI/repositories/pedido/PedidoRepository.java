package com.playpieceAPI.repositories.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.pedido.PedidoModel;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

}
