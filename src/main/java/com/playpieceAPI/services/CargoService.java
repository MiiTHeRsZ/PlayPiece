package com.playpieceAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.CargoModel;
import com.playpieceAPI.repositories.CargoRepository;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<CargoModel> getAllCargos() {
        try {
            return cargoRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
