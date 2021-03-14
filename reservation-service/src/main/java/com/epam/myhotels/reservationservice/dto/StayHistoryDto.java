package com.epam.myhotels.reservationservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class StayHistoryDto implements Serializable {

    private Long id;
    @NotNull
    private Long guestId;
    @NotNull
    private Long reservationId;
    private ReservationDto reservation;
}