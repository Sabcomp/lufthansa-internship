package de.lhind.internship.mini.project.repository;

import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    List<Room> findByHotelIdAndStatus(Long hotelId, RoomStatus status);

    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    boolean existsByHotelIdAndRoomNumber(Long hotelId, String roomNumber);
}
