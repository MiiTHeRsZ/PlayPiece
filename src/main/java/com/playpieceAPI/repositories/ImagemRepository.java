package com.playpieceAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.ImagemModel;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemModel, Long> {

}
