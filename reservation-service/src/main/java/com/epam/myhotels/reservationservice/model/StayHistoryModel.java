package com.epam.myhotels.reservationservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StayHistoryModel implements Serializable {

    private Long id;
    private Long guestId;
    private Long reservationId;
    private ReservationModel reservation;
}