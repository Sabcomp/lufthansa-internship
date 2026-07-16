package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.HotelDTO;
import de.lhind.internship.mini.project.entity.Hotel;
import de.lhind.internship.mini.project.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public void createHotel(HotelDTO hotelDTO){
        Hotel hotel = new Hotel();
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setName(hotelDTO.getName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setStarRating(hotelDTO.getStarRating());

        hotelRepository.save(hotel);
    }

    public List<HotelDTO> getHotels(){
        return hotelRepository.findAll()
                .stream()
                .map(hotel -> HotelDTO.builder().id(hotel.getId())
                        .name(hotel.getName())
                        .address(hotel.getAddress())
                        .city(hotel.getCity())
                        .starRating(hotel.getStarRating()).build())
                .collect(Collectors.toList());
    }

    public HotelDTO getHotel(Long id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty())
            return null;
        return HotelDTO.builder().id(hotel.get().getId())
                .name(hotel.get().getName())
                .address(hotel.get().getAddress())
                .city(hotel.get().getCity())
                .starRating(hotel.get().getStarRating()).build();
    }

    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty())
            return null;
        hotel.get().setName(hotelDTO.getName());
        hotel.get().setAddress(hotelDTO.getAddress());
        hotel.get().setCity(hotelDTO.getCity());
        hotel.get().setStarRating(hotelDTO.getStarRating());
        hotelRepository.save(hotel.get());
        return hotelDTO;
    }
}
