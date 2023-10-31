package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.models.carrinho.CarrinhoModel;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Long> {

    @Query(value = "select c from carrinho c where c.cliente.id = ?1", nativeQuery = false)
    public CarrinhoModel findByClienteId(Long idCliente);

}
