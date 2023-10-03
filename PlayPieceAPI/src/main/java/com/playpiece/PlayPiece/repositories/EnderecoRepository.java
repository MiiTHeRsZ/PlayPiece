package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.Models.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long>{
    
}
