package com.epam.myhotels.reservationservice.dto;

import com.epam.myhotels.reservationservice.entity.ReservationMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ReservationDto implements Serializable {

    private Long id;
    private Date dateIn;
    private Date dateOut;
    private Date checkIn;
    private Date checkOut;
    private ReservationMode mode;
    private Long guestId;
    private Long roomId;
}