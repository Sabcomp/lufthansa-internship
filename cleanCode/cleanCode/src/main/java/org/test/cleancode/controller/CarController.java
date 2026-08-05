package org.test.cleancode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.test.cleancode.dto.CarResponse;
import org.test.cleancode.dto.RegisterCarRequest;
import org.test.cleancode.service.CarService;


@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse registerCar(@RequestBody RegisterCarRequest request) {
        return carService.registerCar(request);
    }
}
