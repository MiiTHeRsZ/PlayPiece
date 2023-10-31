package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.models.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Integer> {

}
