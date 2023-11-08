package com.playpiece.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.models.CargoModel;
import com.playpiece.repositories.CargoRepository;

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
