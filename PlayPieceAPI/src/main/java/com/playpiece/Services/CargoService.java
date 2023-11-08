package com.playpiece.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.Models.CargoModel;
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
