package com.epam.myhotels.hotels.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class RoomModel {

    private Long id;
    private Long hotelId;
    private Integer roomNumber;
    private BigInteger rate;
    private RoomTypeModel roomType;
}