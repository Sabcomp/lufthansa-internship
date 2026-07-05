package org.internship.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.internship.entity.Citizen;
import org.internship.entity.Vehicle;
import org.internship.repository.CitizenRepository;
import org.internship.repository.VehicleRepository;

public class VehicleService {
    private VehicleRepository vehicleRepository;
    private CitizenRepository citizenRepository;
    private EntityManager em;

    public VehicleService(EntityManager em){
        this.em = em;
        vehicleRepository = new VehicleRepository(em);
        citizenRepository = new CitizenRepository(em);
    }

    public Vehicle registerVehicle(String plateNumber, String model, Citizen owner) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        if (plateNumber == null || plateNumber.isEmpty() || model == null || model.isEmpty() || owner == null)
            throw new IllegalArgumentException("Vehicle fields should not be empty");

        if (vehicleRepository.findByPlateNumber(plateNumber) != null)
            throw new EntityExistsException("Vehicle with this plate number already exists");

        if (citizenRepository.findById(owner.getId()) == null)
            throw new EntityNotFoundException("Vehicle can't be assigned to citizen that doesn't exist");

        Vehicle vehicle = vehicleRepository.save(new Vehicle(plateNumber, model, owner));
        em.getTransaction().commit();
        return vehicle;
    }
}
