package com.internship.dto;

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
public class GuestProfileDTO {
    private Long id;
    private String address;
    private LocalDate dateOfBirth;
    private String nationality;
    private String preferredLanguage;
}
