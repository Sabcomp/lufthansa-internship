package org.test.cleancode.repository;

import org.springframework.stereotype.Repository;
import org.test.cleancode.domain.Car;

import java.util.*;

@Repository
public class InMemoryCarRepository implements CarRepository{
    private final Map<Long, Car> carsById = new HashMap<>();
    private long nextId = 1L;


    @Override
    public Car save(Car car) {
        Long carId = car.getId();
        if (carId == null) {
            carId = nextId++;
        }
        Car savedCar = new Car(car.getId(), car.getOwnerName(), car.getPlateNumber(), car.getModel());
        carsById.put(carId, savedCar);
        return savedCar;
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(carsById.values());
    }

    @Override
    public Optional<Car> findByPlateNumber(String plateNumber) {
        return carsById.values()
                .stream()
                .filter(car ->  car.getPlateNumber().equalsIgnoreCase(plateNumber))
                .findFirst();
    }
}
