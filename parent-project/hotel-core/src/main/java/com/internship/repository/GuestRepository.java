package com.internship.repository;

import com.internship.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
