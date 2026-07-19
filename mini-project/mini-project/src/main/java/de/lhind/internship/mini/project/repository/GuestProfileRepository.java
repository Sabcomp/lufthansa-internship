package de.lhind.internship.mini.project.repository;

import de.lhind.internship.mini.project.entity.GuestProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestProfileRepository extends JpaRepository<GuestProfile, Long> {

    Optional<GuestProfile> findByGuestId(Long guestId);

    boolean existsByGuestId(Long guestId);
}
