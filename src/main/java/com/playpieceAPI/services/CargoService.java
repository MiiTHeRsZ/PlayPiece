package com.playpieceAPI.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.CargoModel;
import com.playpieceAPI.repositories.CargoRepository;

@Service
@Slf4j
public class CargoService {

    final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoModel> getAllCargos() {
        List<CargoModel> asda = cargoRepository.findAll();
        asda.forEach(System.out::println);
        return asda;
    }
}
