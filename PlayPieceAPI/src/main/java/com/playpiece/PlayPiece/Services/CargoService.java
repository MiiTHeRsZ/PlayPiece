package com.playpiece.PlayPiece.Services;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpiece.PlayPiece.Models.CargoModel;
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

    public CargoModel patchCargo(int id, CargoModel novoCargo) {

        CargoModel cargo = cargoRepository.findById(id).get();
        try {
            for (Field cargoField : CargoModel.class.getDeclaredFields()) {
                cargoField.setAccessible(true);

                if (cargoField.get(novoCargo) != null
                        && !cargoField.get(novoCargo).equals(cargoField.get(cargo))
                        && cargoField.getName() != "nome") {

                    cargo = cargoRepository.findById(novoCargo.getId()).get();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return cargo;
    }
}
