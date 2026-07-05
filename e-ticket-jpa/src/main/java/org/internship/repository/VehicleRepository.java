package org.internship.repository;

import jakarta.persistence.EntityManager;
import org.internship.entity.Vehicle;

import java.util.List;

public class VehicleRepository {
    private EntityManager em;
    public VehicleRepository(EntityManager em){
        this.em = em;
    }

    public Vehicle save(Vehicle vehicle) {
        em.persist(vehicle);
        return vehicle;
    }

    public Vehicle findById(long id) {
        return em.find(Vehicle.class, id);
    }

    public List<Vehicle> findAll() {
        String jpql = "SELECT e FROM Vehicle e";
        return em.createQuery(jpql, Vehicle.class).getResultList();
    }

    public Vehicle findByPlateNumber(String plateNumber){
        String jpql = "SELECT v FROM Vehicle v WHERE v.plateNumber = :plate";
        return em.createQuery(jpql, Vehicle.class)
                .setParameter("plate", plateNumber)
                .getSingleResultOrNull();
    }

    public Vehicle update(Vehicle vehicle) {
        return em.merge(vehicle);
    }

    public void delete(Vehicle vehicle) {
        em.remove(em.contains(vehicle) ? vehicle : em.merge(vehicle));
    }
}
