package com.playpiece.PlayPiece.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.playpiece.PlayPiece.Models.ContatoModel;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {
    }
