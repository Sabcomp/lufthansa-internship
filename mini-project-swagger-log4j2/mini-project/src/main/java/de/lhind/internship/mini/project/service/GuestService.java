package de.lhind.internship.mini.project.service;

import de.lhind.internship.mini.project.dto.GuestDTO;
import de.lhind.internship.mini.project.entity.Guest;
import de.lhind.internship.mini.project.exception.DuplicateEmailException;
import de.lhind.internship.mini.project.exception.ResourceNotFoundException;
import de.lhind.internship.mini.project.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;
    private static final Logger logger = LogManager.getLogger(GuestService.class);

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public void createGuest(GuestDTO dto) {
        logger.trace("Entering createGuest()");
        logger.debug("Guest data before insert: {}, {}, {}, {}", dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber());

        if (guestRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            logger.error("createGuest() failed - email must be unique");
            throw new DuplicateEmailException("A guest with email " + dto.getEmail() + " already exists");
        }

        Guest guest = new Guest();
        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setEmail(dto.getEmail());
        guest.setPhoneNumber(dto.getPhoneNumber());

        guestRepository.save(guest);
        logger.info("Guest added successfully");
    }

    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll()
                .stream()
                .map(guest -> toDto(guest))
                .toList();
    }

    public GuestDTO getGuestById(Long id) {
        return toDto(findGuest(id));
    }

    public GuestDTO updateGuest(Long id, GuestDTO dto) {
        Guest guest = findGuest(id);

        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setEmail(dto.getEmail());
        guest.setPhoneNumber(dto.getPhoneNumber());
        return toDto(guestRepository.save(guest));
    }

    public Guest findGuest(Long id) {
        Optional<Guest> guest = guestRepository.findById(id);
        if (guest.isEmpty())
            throw new ResourceNotFoundException("Guest with ID " + id + " was not found");
        return guest.get();
    }

    private GuestDTO toDto(Guest guest) {
        return GuestDTO.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .email(guest.getEmail())
                .phoneNumber(guest.getPhoneNumber())
                .build();
    }
}
