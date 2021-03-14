package com.epam.myhotels.reservationservice.dto;

import com.epam.myhotels.reservationservice.entity.ReservationMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ReservationDto implements Serializable {

    private Long id;
    @NotNull
    private Date dateIn;
    private Date dateOut;
    private Date checkIn;
    private Date checkOut;
    @NotNull
    private ReservationMode mode;
    @NotNull
    private Long guestId;
    @NotNull
    private Long roomId;
}