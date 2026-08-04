package de.lhind.internship.mini.project.controller;

import de.lhind.internship.mini.project.dto.GuestDTO;
import de.lhind.internship.mini.project.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@SecurityRequirement(name = "bearer")
@Tag(name = "Guests", description = "Guest operations")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(summary = "Add new guest")
    public ResponseEntity<Void> createGuest(@Valid @RequestBody GuestDTO dto) {
        guestService.createGuest(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get list of all guests")
    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAllGuests() {
        List<GuestDTO> guests = guestService.getAllGuests();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @Operation(summary = "Get guest by id")
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuestById(
            @Parameter(description = "Guest ID") @PathVariable Long id) {
        GuestDTO guestDTO = guestService.getGuestById(id);
        return new ResponseEntity<>(guestDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update guest information")
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(
            @Parameter(description = "Guest ID") @PathVariable Long id,
            @Valid @RequestBody GuestDTO dto) {
        GuestDTO guestDTO = guestService.updateGuest(id, dto);
        return new ResponseEntity<>(guestDTO, HttpStatus.OK);
    }
}
