package com.playpiece.PlayPiece.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.models.CargoModel;
import com.playpiece.PlayPiece.repositories.CargoRepository;

@Service
public class CargoService {

    final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoModel> getAllCargos() {
        return cargoRepository.findAll();
    }
}
