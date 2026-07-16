package de.lhind.internship.mini.project.repository;

import de.lhind.internship.mini.project.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
