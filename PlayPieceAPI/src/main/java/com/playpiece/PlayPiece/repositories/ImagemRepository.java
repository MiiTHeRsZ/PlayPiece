package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.Models.ImagemModel;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemModel, Long> {

}
