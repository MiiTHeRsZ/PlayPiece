package com.playpiece.PlayPiece.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.playpiece.PlayPiece.Models.PessoaModel;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Integer> {
    List<PessoaModel> findAll();

    PessoaModel findById(int id);

    @Query(value = "Select u.* from pessoa u where u.cpf like %?1%", nativeQuery = true)
    List<PessoaModel> findByCpfLike(Long cpf);

    List<PessoaModel> findByNomeContaining(String nome);

    
}
