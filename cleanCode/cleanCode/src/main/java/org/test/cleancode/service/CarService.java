package org.test.cleancode.service;

import org.springframework.stereotype.Service;
import org.test.cleancode.domain.Car;
import org.test.cleancode.dto.CarResponse;
import org.test.cleancode.dto.RegisterCarRequest;
import org.test.cleancode.exception.InvalidRegistrationException;
import org.test.cleancode.exception.PlateNumberAlreadyExistsException;
import org.test.cleancode.repository.CarRepository;


@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public CarResponse registerCar(RegisterCarRequest request) {
        validateRequest(request);

        String normalizedPlateNumber = request.getPlateNumber().trim().toUpperCase();
        carRepository.findByPlateNumber(normalizedPlateNumber).ifPresent(car -> {
            throw new PlateNumberAlreadyExistsException("Plate number already exists: " + normalizedPlateNumber);
        });

        Car newCar = new Car(null, request.getOwnerName().trim(), normalizedPlateNumber, request.getModel().trim());
        Car savedCar = carRepository.save(newCar);

        return new CarResponse(savedCar.getId(), savedCar.getOwnerName(), savedCar.getPlateNumber(), savedCar.getModel());
    }

    private void validateRequest(RegisterCarRequest request){
        if (request == null)
            throw new InvalidRegistrationException("Request is required");
        if (isBlank(request.getOwnerName()))
            throw new InvalidRegistrationException("Owner name is required");
        if (isBlank(request.getModel()))
            throw new InvalidRegistrationException("Model is required");
        if (isBlank(request.getPlateNumber()))
            throw new InvalidRegistrationException("Plate number is required");
    }

    private boolean isBlank(String value){
        return value == null || value.trim().isEmpty();
    }
}
