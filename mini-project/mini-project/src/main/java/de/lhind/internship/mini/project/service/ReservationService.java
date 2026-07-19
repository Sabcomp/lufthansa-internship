package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.ReservationDTO;
import de.lhind.internship.mini.project.dto.ReservationRequestDTO;
import de.lhind.internship.mini.project.dto.RoomReservationCountDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.entity.Reservation;
import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.ReservationStatus;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.exception.InvalidReservationDateException;
import de.lhind.internship.mini.project.exception.ResourceNotFoundException;
import de.lhind.internship.mini.project.exception.RoomCapacityExceededException;
import de.lhind.internship.mini.project.exception.RoomNotAvailableException;
import de.lhind.internship.mini.project.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestService guestService;
    private final RoomService roomService;

    public ReservationService(ReservationRepository reservationRepository,
                               GuestService guestService,
                               RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.guestService = guestService;
        this.roomService = roomService;
    }

    public void createReservation(ReservationRequestDTO dto) {
        // Date validation
        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            throw new InvalidReservationDateException("Check-out date must be after check-in date");
        }

        Guest guest = guestService.findGuest(dto.getGuestId());
        Room room = roomService.findRoom(dto.getRoomId());

        // Room status rule
        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            throw new RoomNotAvailableException("Room with ID " + room.getId() + " is under maintenance and cannot be reserved");
        }

        // Room capacity rule
        if (dto.getNumberOfGuests() > room.getCapacity()) {
            throw new RoomCapacityExceededException(
                    "Room capacity (" + room.getCapacity() + ") is less than the number of guests (" + dto.getNumberOfGuests() + ")");
        }

        // Overlap rule
        long overlapping = reservationRepository.countOverlappingReservations(
                room.getId(), dto.getCheckInDate(), dto.getCheckOutDate());
        if (overlapping > 0) {
            throw new RoomNotAvailableException(
                    "Room with ID " + room.getId() + " already has an active reservation overlapping the requested dates");
        }

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.PENDING);

        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> toDto(reservation))
                .toList();
    }

    public ReservationDTO getReservationById(Long id) {
        return toDto(findReservation(id));
    }

    public ReservationDTO updateStatus(Long id, ReservationStatus status) {
        Reservation reservation = findReservation(id);
        reservation.setStatus(status);
        return toDto(reservationRepository.save(reservation));
    }

    public void cancelReservation(Long id) {
        Reservation reservation = findReservation(id);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getReservationsByGuest(Long guestId) {
        guestService.findGuest(guestId);
        return reservationRepository.findByGuestId(guestId)
                .stream()
                .map(reservation -> toDto(reservation))
                .toList();
    }

    public List<RoomReservationCountDTO> getMostReservedRooms() {
        return reservationRepository.findMostReservedRooms()
                .stream()
                .map(row -> new RoomReservationCountDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }

    private Reservation findReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty())
            throw new ResourceNotFoundException("Reservation with ID " + id + " was not found");
        return reservation.get();
    }

    private ReservationDTO toDto(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .guestId(reservation.getGuest().getId())
                .roomId(reservation.getRoom().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .numberOfGuests(reservation.getNumberOfGuests())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
