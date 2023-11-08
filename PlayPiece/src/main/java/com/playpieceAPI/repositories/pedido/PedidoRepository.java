package com.playpiece.repositories.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.models.pedido.PedidoModel;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

}
