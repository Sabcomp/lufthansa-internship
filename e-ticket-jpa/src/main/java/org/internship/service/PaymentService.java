package org.internship.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.internship.entity.Citizen;
import org.internship.entity.Fine;
import org.internship.entity.FineStatus;
import org.internship.entity.Payment;
import org.internship.repository.CitizenRepository;
import org.internship.repository.FineRepository;
import org.internship.repository.PaymentRepository;

import java.util.List;

public class PaymentService {
    private EntityManager em;
    private FineRepository fineRepository;
    private PaymentRepository paymentRepository;
    private CitizenRepository citizenRepository;

    public PaymentService(EntityManager em) {
        this.em = em;
        this.fineRepository = new FineRepository(em);
        this.paymentRepository = new PaymentRepository(em);
        this.citizenRepository = new CitizenRepository(em);
    }

    public Payment payFine(long fineId, double amount, long citizenId) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        Fine fine = fineRepository.findById(fineId);
        if (fine == null)
            throw new EntityNotFoundException("Fine with id " + fineId + " doesn't exist");

        if (amount != fine.getAmount())
            throw new IllegalArgumentException("Payment amount should match the fine amount of " + fine.getAmount());

        if (fine.getStatus() == FineStatus.PAID)
            throw new IllegalStateException("Fine #" + fineId + " has already been paid and cannot be paid again");

        if (fine.getStatus() == FineStatus.CANCELLED)
            throw new IllegalStateException("Fine #" + fineId + " has been cancelled and cannot be paid");

        Citizen payer = citizenRepository.findById(citizenId);
        if (payer == null)
            throw new EntityNotFoundException("Payment can't be made by citizen that doesn't exist");

        fine.setStatus(FineStatus.PAID);
        Fine updatedFine = fineRepository.update(fine);

        Payment payment = new Payment(amount, updatedFine, payer);
        paymentRepository.save(payment);

        em.getTransaction().commit();
        return payment;
    }

    public Payment findPaymentForFine(long fineId) {
        return paymentRepository.findByFineId(fineId);
    }

    public List<Payment> findPaymentsByCitizen(long citizenId) {
        return paymentRepository.findByCitizen(citizenId);
    }

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
}
