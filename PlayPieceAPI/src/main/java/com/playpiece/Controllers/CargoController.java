package com.playpiece.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playpiece.models.CargoModel;
import com.playpiece.services.CargoService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "cargo")
public class CargoController {

    final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoModel>> getContatos() {
        return ResponseEntity.ok().body(cargoService.getAllCargos());
    }

}
