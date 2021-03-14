package com.epam.myhotels.reservationservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StayHistoryDto implements Serializable {

    private Long id;
    private Long guestId;
    private Long reservationId;
    private ReservationDto reservation;
}