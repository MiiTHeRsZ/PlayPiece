package com.playpieceAPI.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    public Page<ProdutoModel> findAllByOrderByProdutoIdDesc(Pageable pageable);

    public List<ProdutoModel> findAllByOrderByProdutoIdDesc();

    @Query(value = "select p from produto p where p.nome like %?1% order by id desc")
    public Page<ProdutoModel> findByNomeContaining(String nome, Pageable pageable);
}
