package com.internship.controller;

import com.internship.dto.HotelDTO;
import com.internship.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Void> createHotel(@Valid @RequestBody HotelDTO dto) {
        hotelService.createHotel(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        HotelDTO hotelDTO =  hotelService.getHotelById(id);
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelDTO dto) {
        HotelDTO hotelDTO = hotelService.updateHotel(id, dto);
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchByCity(@RequestParam String city) {
        List<HotelDTO> hotels = hotelService.searchByCity(city);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}
