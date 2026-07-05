package org.internship.repository;

import jakarta.persistence.EntityManager;
import org.internship.entity.Fine;

import java.util.List;

public class FineRepository {
    private EntityManager em;

    public FineRepository(EntityManager em){
        this.em = em;
    }

    public Fine save(Fine fine) {
        em.persist(fine);
        return fine;
    }

    public Fine findById(long id) {
        return em.find(Fine.class, id);
    }

    public List<Fine> findAll() {
        String jpql = "SELECT f FROM Fine f";
        return em.createQuery(jpql, Fine.class).getResultList();
    }

    public List<Fine> findByCitizen(long citizenId) {
        String jpql = "SELECT f FROM Fine f WHERE f.vehicle.owner.id = :citizenId";
        return em.createQuery(jpql, Fine.class)
                .setParameter("citizenId", citizenId)
                .getResultList();
    }

    public List<Fine> findByPlateNumber(String plateNumber) {
        String jpql = "SELECT f FROM Fine f WHERE f.vehicle.plateNumber = :plate";
        return em.createQuery(jpql, Fine.class)
                .setParameter("plate", plateNumber)
                .getResultList();
    }

    public Fine update(Fine fine) {
        return em.merge(fine);
    }

    public void delete(Fine fine) {
        em.remove(em.contains(fine) ? fine : em.merge(fine));
    }
}
