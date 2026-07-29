package org.internship.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.internship.entity.Fine;
import org.internship.entity.FineStatus;
import org.internship.entity.Police;
import org.internship.entity.Vehicle;
import org.internship.repository.FineRepository;
import org.internship.repository.PoliceRepository;
import org.internship.repository.VehicleRepository;

import java.util.List;

public class FineService {
    private EntityManager em;
    private FineRepository fineRepository;
    private PoliceRepository policeRepository;
    private VehicleRepository vehicleRepository;

    public FineService(EntityManager em) {
        this.em = em;
        this.fineRepository = new FineRepository(em);
        this.policeRepository = new PoliceRepository(em);
        this.vehicleRepository = new VehicleRepository(em);
    }

    public Fine createFine(String reason, int amount, Police officer, Vehicle vehicle) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        if (reason == null || reason.isEmpty() || officer == null || vehicle == null)
            throw new IllegalArgumentException("Fine fields should not be empty");

        if (amount <= 0)
            throw new IllegalArgumentException("Fine amount should be greater than 0");

        if (policeRepository.findById(officer.getId()) == null)
            throw new EntityNotFoundException("Fine can't be issued by an officer that doesn't exist");

        if (vehicleRepository.findById(vehicle.getId()) == null)
            throw new EntityNotFoundException("Fine can't be issued to a vehicle that doesn't exist");

        Fine fine = fineRepository.save(new Fine(reason, amount, FineStatus.UNPAID, officer, vehicle));
        em.getTransaction().commit();
        return fine;
    }

    public List<Fine> findAllFines() {
        return fineRepository.findAll();
    }

    public List<Fine> findFinesByCitizen(long citizenId) {
        return fineRepository.findByCitizen(citizenId);
    }

    public List<Fine> findFinesByPlateNumber(String plateNumber) {
        return fineRepository.findByPlateNumber(plateNumber);
    }

    public Fine updateFineReason(long fineId, String newReason) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        if (newReason == null || newReason.isEmpty())
            throw new IllegalArgumentException("Reason should not be empty");

        Fine fine = fineRepository.findById(fineId);
        if (fine == null)
            throw new EntityNotFoundException("Fine with id " + fineId + " doesn't exist");

        fine.setReason(newReason);
        Fine updated = fineRepository.update(fine);
        em.getTransaction().commit();
        return updated;
    }

    public Fine cancelFine(long fineId) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        Fine fine = fineRepository.findById(fineId);
        if (fine == null)
            throw new EntityNotFoundException("Fine with id " + fineId + " doesn't exist");

        if (fine.getStatus() == FineStatus.PAID)
            throw new IllegalStateException("Fine has already been paid and cannot be cancelled");

        if (fine.getStatus() == FineStatus.CANCELLED)
            throw new IllegalStateException("Fine has already been cancelled");

        fine.setStatus(FineStatus.CANCELLED);
        Fine updated = fineRepository.update(fine);
        em.getTransaction().commit();
        return updated;
    }
}
