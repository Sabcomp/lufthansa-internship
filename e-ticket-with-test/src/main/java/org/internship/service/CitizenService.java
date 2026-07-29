package org.internship.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.internship.entity.Citizen;
import org.internship.repository.CitizenRepository;

public class CitizenService {
    private CitizenRepository citizenRepository;
    private EntityManager em;

    public CitizenService(EntityManager em){
        this.em = em;
        citizenRepository = new CitizenRepository(em);
    }


    public Citizen createCitizen(String name, String driverId, String username, String password) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        if (name == null || name.isEmpty() || driverId == null || driverId.isEmpty()
                || username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new IllegalArgumentException("Citizen fields should not be empty");

        if (citizenRepository.findByDriverId(driverId) != null || citizenRepository.findByUsername(username) != null)
            throw new EntityExistsException("Citizen username and driver id should be unique");

        Citizen citizen = citizenRepository.save(new Citizen(name, username, password, driverId));
        em.getTransaction().commit();
        return citizen;
    }
}
