package com.playpieceAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Integer> {

}
