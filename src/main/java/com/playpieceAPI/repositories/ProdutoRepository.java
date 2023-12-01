package com.playpieceAPI.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.playpieceAPI.models.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    public Page<ProdutoModel> findAllByOrderByProdutoIdDesc(Pageable pageable);

    @Query("select DISTINCT  p from produto p left  join FETCH  p.listaImagens i order by p.id desc, CASE WHEN i.padrao = true THEN 0 ELSE 1 END ASC")
    public List<ProdutoModel> findAllByOrderByProdutoIdDescAndImagemPadrao();

    @Query(value = "select p from produto p where p.nome like %?1% order by id desc")
    public Page<ProdutoModel> findByNomeContaining(String nome, Pageable pageable);

    @Query("select DISTINCT p from produto p left join FETCH p.listaImagens i where p.id = ?1 order by i.padrao desc")
    public ProdutoModel findByIdOrderByPadrao(Long produtoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM imagem i WHERE i.produto.id = ?1")
    public void delByIdProduto(Long produtoId);
}
