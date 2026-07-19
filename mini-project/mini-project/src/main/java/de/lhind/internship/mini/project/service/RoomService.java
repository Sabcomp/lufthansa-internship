package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.RoomDTO;
import de.lhind.internship.mini.project.dto.RoomStatusDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.entity.Room;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.exception.DuplicateRoomNumberException;
import de.lhind.internship.mini.project.exception.ResourceNotFoundException;
import de.lhind.internship.mini.project.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomService(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    public void addRoomToHotel(Long hotelId, RoomDTO dto) {
        Hotel hotel = hotelService.findHotel(hotelId);

        if (roomRepository.existsByHotelIdAndRoomNumber(hotelId, dto.getRoomNumber())) {
            throw new DuplicateRoomNumberException(
                    "Room number " + dto.getRoomNumber() + " already exists for hotel " + hotelId);
        }

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setCapacity(dto.getCapacity());
        room.setPricePerNight(dto.getPricePerNight());
        room.setStatus(dto.getStatus());

        roomRepository.save(room);
    }

    public List<RoomDTO> getRoomsForHotel(Long hotelId) {
        hotelService.findHotel(hotelId);
        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(room -> toDto(room))
                .toList();
    }

    public RoomDTO getRoomById(Long id) {
        return toDto(findRoom(id));
    }

    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Room room = findRoom(id);

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setCapacity(dto.getCapacity());
        room.setPricePerNight(dto.getPricePerNight());
        room.setStatus(dto.getStatus());

        return toDto(roomRepository.save(room));
    }

    public RoomDTO updateRoomStatus(Long id, RoomStatusDTO dto) {
        Room room = findRoom(id);
        room.setStatus(dto.getStatus());
        return toDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id) {
        Room room = findRoom(id);
        roomRepository.delete(room);
    }

    public List<RoomDTO> searchRooms(Long hotelId, RoomStatus status) {
        return roomRepository.findByHotelIdAndStatus(hotelId, status)
                .stream()
                .map(room -> toDto(room))
                .toList();
    }

    public Room findRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty())
            throw new ResourceNotFoundException("Room with ID " + id + " was not found");
        return room.get();
    }

    private RoomDTO toDto(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .build();
    }
}
