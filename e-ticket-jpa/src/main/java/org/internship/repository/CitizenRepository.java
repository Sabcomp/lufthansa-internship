package org.internship.repository;

import jakarta.persistence.EntityManager;
import org.internship.entity.Citizen;

import java.util.List;

public class CitizenRepository {
    private EntityManager em;

    public CitizenRepository(EntityManager em){
        this.em = em;
    }

    public Citizen save(Citizen citizen) {
        em.persist(citizen);
        return citizen;
    }

    public Citizen findById(long id) {
        return em.find(Citizen.class, id);
    }

    public List<Citizen> findAll() {
        String jpql = "SELECT c FROM Citizen c";
        return em.createQuery(jpql, Citizen.class).getResultList();
    }

    public Citizen findByDriverId(String driverId){
        String jpql = "SELECT c FROM Citizen c WHERE c.driverId = :driverId";
        return em.createQuery(jpql, Citizen.class)
                .setParameter("driverId", driverId)
                .getSingleResultOrNull();
    }

    public Citizen findByUsername(String username){
        String jpql = "SELECT c FROM Citizen c WHERE c.username = :username";
        return em.createQuery(jpql, Citizen.class)
                .setParameter("username", username)
                .getSingleResultOrNull();
    }

    public Citizen update(Citizen citizen) {
        return em.merge(citizen);
    }

    public void delete(Citizen citizen) {
        em.remove(em.contains(citizen) ? citizen : em.merge(citizen));
    }
}
