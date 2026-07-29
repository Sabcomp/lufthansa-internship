package com.internship.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private Long id;

    @NotNull(message = "Guest id is required")
    private Long guestId;

    @NotNull(message = "Room id is required")
    private Long roomId;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotNull(message = "Number of guests is required")
    @Positive(message = "Number of guests must be positive")
    private Integer numberOfGuests;
}
