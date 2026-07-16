package de.lhind.internship.mini.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private Long id;

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Hotel city is required")
    private String city;

    @NotBlank(message = "Hotel address is required")
    private String address;

    @NotBlank(message = "Hotel rating is required")
    @Min(value = 1, message = "Rating should be at least 1")
    @Max(value = 5, message = "Rating should be at most 5")
    private Integer starRating;
}
