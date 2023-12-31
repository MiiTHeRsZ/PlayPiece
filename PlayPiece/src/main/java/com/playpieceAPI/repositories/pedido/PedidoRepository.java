package com.playpieceAPI.repositories.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.pedido.PedidoModel;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    @Query("select p from pedido p where p.cliente.id = ?1")
    List<PedidoModel> findAllByClienteId(Long id);

}
