package com.playpiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.Models.ImagemModel;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemModel, Long> {

}
