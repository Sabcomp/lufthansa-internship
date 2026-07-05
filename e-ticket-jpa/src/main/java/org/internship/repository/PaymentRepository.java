package org.internship.repository;

import jakarta.persistence.EntityManager;
import org.internship.entity.Payment;

import java.util.List;

public class PaymentRepository {
    private EntityManager em;

    public PaymentRepository(EntityManager em){
        this.em = em;
    }

    public Payment save(Payment payment) {
        em.persist(payment);
        return payment;
    }

    public Payment findById(long id) {
        return em.find(Payment.class, id);
    }

    public List<Payment> findAll() {
        String jpql = "SELECT p FROM Payment p";
        return em.createQuery(jpql, Payment.class).getResultList();
    }

    public Payment findByFineId(long fineId) {
        String jpql = "SELECT p FROM Payment p WHERE p.fine.id = :fineId";
        return em.createQuery(jpql, Payment.class)
                .setParameter("fineId", fineId)
                .getSingleResultOrNull();
    }

    public List<Payment> findByCitizen(long citizenId) {
        String jpql = "SELECT p FROM Payment p WHERE p.payer.id = :citizenId";
        return em.createQuery(jpql, Payment.class)
                .setParameter("citizenId", citizenId)
                .getResultList();
    }

    public Payment update(Payment payment) {
        return em.merge(payment);
    }

    public void delete(Payment payment) {
        em.remove(em.contains(payment) ? payment : em.merge(payment));
    }
}
