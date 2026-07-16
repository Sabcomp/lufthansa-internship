package de.lhind.internship.mini.project.controller;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Void> createHotel(@Valid @RequestBody HotelDTO hotelDTO){
        hotelService.createHotel(hotelDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getHotels(){
        List<HotelDTO> hotels = hotelService.getHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long id){
        HotelDTO hotelDTO = hotelService.getHotel(id);
        if (hotelDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelDTO hotelDTO){
        HotelDTO updatedHotelDTO = hotelService.updateHotel(id, hotelDTO);
        if (updatedHotelDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedHotelDTO, HttpStatus.OK);
    }


}
