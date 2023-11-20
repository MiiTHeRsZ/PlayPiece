package com.playpieceAPI.repositories.carrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.carrinho.CarrinhoModel;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Long> {

    @Query(value = "SELECT c FROM carrinho c WHERE c.cliente.id = ?1", nativeQuery = false)
    public CarrinhoModel findByClienteIdAndAtivo(Long idCliente);

}
