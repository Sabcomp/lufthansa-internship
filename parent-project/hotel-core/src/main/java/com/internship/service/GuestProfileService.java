package com.internship.service;

import com.internship.dto.GuestProfileDTO;
import com.internship.entity.Guest;
import com.internship.entity.GuestProfile;
import com.internship.exception.DuplicateProfileException;
import com.internship.exception.ResourceNotFoundException;
import com.internship.repository.GuestProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GuestProfileService {

    private final GuestProfileRepository guestProfileRepository;
    private final GuestService guestService;

    public GuestProfileService(GuestProfileRepository guestProfileRepository, GuestService guestService) {
        this.guestProfileRepository = guestProfileRepository;
        this.guestService = guestService;
    }

    public void createProfile(Long guestId, GuestProfileDTO dto) {
        if (guestProfileRepository.existsByGuestId(guestId))
            throw new DuplicateProfileException("A profile for guest with id " + guestId + " already exists");

        Guest guest = guestService.findGuest(guestId);

        GuestProfile profile = new GuestProfile();
        profile.setGuest(guest);
        profile.setAddress(dto.getAddress());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setNationality(dto.getNationality());
        profile.setPreferredLanguage(dto.getPreferredLanguage());

        guestProfileRepository.save(profile);
    }

    public GuestProfileDTO getProfileByGuestId(Long guestId) {
        // check if guest with the given guestId exists
        guestService.findGuest(guestId);

        Optional<GuestProfile> profile = guestProfileRepository.findByGuestId(guestId);
        if (profile.isEmpty())
            throw new ResourceNotFoundException("Guest profile for guest ID " + guestId + " was not found");
        return toDto(profile.get());
    }

    private GuestProfileDTO toDto(GuestProfile profile) {
        return GuestProfileDTO.builder()
                .id(profile.getId())
                .address(profile.getAddress())
                .dateOfBirth(profile.getDateOfBirth())
                .nationality(profile.getNationality())
                .preferredLanguage(profile.getPreferredLanguage())
                .build();
    }
}
