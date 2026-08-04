package org.test.cleancode.repository;

import org.test.cleancode.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    List<Car> findAll();
    Optional<Car> findByPlateNumber(String plateNumber);
}
