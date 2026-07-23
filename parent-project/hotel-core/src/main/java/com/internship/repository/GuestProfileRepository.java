package com.internship.repository;

import com.internship.entity.GuestProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestProfileRepository extends JpaRepository<GuestProfile, Long> {

    Optional<GuestProfile> findByGuestId(Long guestId);

    boolean existsByGuestId(Long guestId);
}
