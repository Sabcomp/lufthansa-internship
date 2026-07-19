package de.lhind.internship.mini.project.controller;

import de.lhind.internship.mini.project.dto.ReservationDTO;
import de.lhind.internship.mini.project.dto.ReservationRequestDTO;
import de.lhind.internship.mini.project.dto.RoomReservationCountDTO;
import de.lhind.internship.mini.project.entity.ReservationStatus;
import de.lhind.internship.mini.project.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/api/reservations")
    public ResponseEntity<Void> createReservation(@Valid @RequestBody ReservationRequestDTO dto) {
        reservationService.createReservation(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/api/reservations/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PatchMapping("/api/reservations/{id}/status")
    public ResponseEntity<ReservationDTO> updateStatus(@PathVariable Long id,
                                                       @RequestBody ReservationStatus status) {
        ReservationDTO reservation = reservationService.updateStatus(id, status);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/api/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/reservations/guest/{guestId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByGuest(@PathVariable Long guestId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByGuest(guestId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/api/reports/most-reserved-rooms")
    public ResponseEntity<List<RoomReservationCountDTO>> getMostReservedRooms() {
        List<RoomReservationCountDTO> reservationCounts = reservationService.getMostReservedRooms();
        return new ResponseEntity<>(reservationCounts, HttpStatus.OK);
    }
}
