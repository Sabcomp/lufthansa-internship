package com.internship.service;

import com.internship.dto.HotelDTO;
import com.internship.entity.Hotel;
import com.internship.exception.ResourceNotFoundException;
import com.internship.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public void createHotel(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setCity(dto.getCity());
        hotel.setAddress(dto.getAddress());
        hotel.setStarRating(dto.getStarRating());

        hotelRepository.save(hotel);
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(hotel -> toDto(hotel))
                .toList();
    }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = findHotel(id);
        return toDto(hotel);
    }

    public HotelDTO updateHotel(Long id, HotelDTO dto) {
        Hotel hotel = findHotel(id);
        hotel.setName(dto.getName());
        hotel.setCity(dto.getCity());
        hotel.setAddress(dto.getAddress());
        hotel.setStarRating(dto.getStarRating());
        return toDto(hotelRepository.save(hotel));
    }

    public void deleteHotel(Long id) {
        Hotel hotel = findHotel(id);
        hotelRepository.delete(hotel);
    }

    public List<HotelDTO> searchByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city)
                .stream()
                .map(hotel -> toDto(hotel))
                .toList();
    }

    public Hotel findHotel(Long id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isEmpty())
            throw new ResourceNotFoundException("Hotel with ID " + id + " was not found");
        return hotel.get();
    }

    public HotelDTO toDto(Hotel hotel){
        return HotelDTO.builder().id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .starRating(hotel.getStarRating()).build();
    }
}
