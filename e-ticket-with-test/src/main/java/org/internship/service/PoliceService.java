package org.internship.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.internship.entity.Police;
import org.internship.repository.PoliceRepository;

public class PoliceService {
    private PoliceRepository policeRepository;
    private EntityManager em;

    public PoliceService(EntityManager em){
        policeRepository = new PoliceRepository(em);
        this.em = em;
    }

    public Police createOfficer(String name, String badgeId, String username, String password) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        if (name == null || name.isEmpty() || badgeId == null || badgeId.isEmpty()
                || username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new IllegalArgumentException("Police officer fields should not be empty");

        if (policeRepository.findByBadgeId(badgeId) != null || policeRepository.findByUsername(username) != null)
            throw new EntityExistsException("Officer username and badge number should be unique");

        Police officer = policeRepository.save(new Police(name, username, password, badgeId));
        em.getTransaction().commit();
        return officer;
    }
}
