package com.internship.controller;

import de.lhind.internship.mini.project.dto.RoomDTO;
import de.lhind.internship.mini.project.dto.RoomStatusDTO;
import de.lhind.internship.mini.project.entity.RoomStatus;
import de.lhind.internship.mini.project.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/api/hotels/{hotelId}/rooms")
    public ResponseEntity<Void> addRoom(@PathVariable Long hotelId, @Valid @RequestBody RoomDTO dto) {
        roomService.addRoomToHotel(hotelId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomsForHotel(@PathVariable Long hotelId) {
        List<RoomDTO> rooms = roomService.getRoomsForHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/api/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PutMapping("/api/rooms/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO dto) {
        RoomDTO room = roomService.updateRoom(id, dto);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PatchMapping("/api/rooms/{id}/status")
    public ResponseEntity<RoomDTO> updateRoomStatus(@PathVariable Long id, @RequestBody RoomStatusDTO status) {
        RoomDTO room = roomService.updateRoomStatus(id, status);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/api/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/rooms/search")
    public ResponseEntity<List<RoomDTO>> searchRooms(@RequestParam Long hotelId, @RequestParam RoomStatus status) {
        List<RoomDTO> rooms = roomService.searchRooms(hotelId, status);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
