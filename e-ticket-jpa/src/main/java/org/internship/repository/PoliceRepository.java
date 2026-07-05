package org.internship.repository;

import jakarta.persistence.EntityManager;
import org.internship.entity.Police;

import java.util.List;

public class PoliceRepository {
    private EntityManager em;

    public PoliceRepository(EntityManager em){
        this.em = em;
    }

    public Police save(Police officer) {
        em.persist(officer);
        return officer;
    }

    public Police findById(long id) {
        return em.find(Police.class, id);
    }

    public Police findByBadgeId(String badgeId){
        String jpql = "SELECT e FROM Police e WHERE e.badgeId = :badgeNumber";
        return em.createQuery(jpql, Police.class)
                .setParameter("badgeNumber", badgeId)
                .getSingleResultOrNull();
    }

    public Police findByUsername(String username){
        String jpql = "SELECT e FROM Police e WHERE e.username = :username";
        return em.createQuery(jpql, Police.class)
                .setParameter("username", username)
                .getSingleResultOrNull();
    }

    public List<Police> findAll() {
        String jpql = "SELECT e FROM Police e";
        return em.createQuery(jpql, Police.class).getResultList();
    }

    public Police update(Police officer) {
        return em.merge(officer);
    }

    public void delete(Police officer) {
        em.remove(em.contains(officer) ? officer : em.merge(officer));
    }
}
