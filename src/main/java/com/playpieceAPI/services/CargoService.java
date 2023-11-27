package com.playpieceAPI.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpieceAPI.models.CargoModel;
import com.playpieceAPI.repositories.CargoRepository;

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
