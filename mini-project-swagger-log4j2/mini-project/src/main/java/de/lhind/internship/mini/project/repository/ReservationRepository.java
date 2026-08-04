package de.lhind.internship.mini.project.repository;

import de.lhind.internship.mini.project.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByGuestId(Long guestId);

    // JPQL query: count overlapping active reservations for a room and date range
    @Query("""
            SELECT COUNT(r)
            FROM Reservation r
            WHERE r.room.id = :roomId
            AND r.status <> 'CANCELLED'
            AND r.checkInDate < :checkOutDate
            AND r.checkOutDate > :checkInDate
            """)
    long countOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    // Native SQL query: top five most frequently reserved rooms
    @Query(value = """
            SELECT r.id AS room_id,
                   r.room_number AS room_number,
                   COUNT(res.id) AS reservation_count
            FROM rooms r
            JOIN reservations res ON res.room_id = r.id
            GROUP BY r.id, r.room_number
            ORDER BY reservation_count DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> findMostReservedRooms();
}
