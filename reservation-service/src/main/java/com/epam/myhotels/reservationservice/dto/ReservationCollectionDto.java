package com.epam.myhotels.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCollectionDto implements Serializable {

    List<ReservationDto> reservations;
}