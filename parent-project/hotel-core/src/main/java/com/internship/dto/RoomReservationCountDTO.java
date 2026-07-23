package com.internship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationCountDTO {
    private Long roomId;
    private String roomNumber;
    private Long reservationCount;
}
