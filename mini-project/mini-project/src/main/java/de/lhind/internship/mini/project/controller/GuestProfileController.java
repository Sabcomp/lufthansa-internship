package de.lhind.internship.mini.project.controller;

import de.lhind.internship.mini.project.dto.GuestProfileDTO;
import de.lhind.internship.mini.project.service.GuestProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests/{guestId}/profile")
public class GuestProfileController {

    private final GuestProfileService guestProfileService;

    public GuestProfileController(GuestProfileService guestProfileService) {
        this.guestProfileService = guestProfileService;
    }

    @PostMapping
    public ResponseEntity<Void> createProfile(@PathVariable Long guestId, @Valid @RequestBody GuestProfileDTO dto) {
        guestProfileService.createProfile(guestId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GuestProfileDTO> getProfile(@PathVariable Long guestId) {
        GuestProfileDTO guestProfileDTO = guestProfileService.getProfileByGuestId(guestId);
        return new ResponseEntity<>(guestProfileDTO, HttpStatus.OK);
    }
}
