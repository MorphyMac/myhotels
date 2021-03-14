package com.epam.myhotels.hotels.model;

import com.epam.myhotels.hotels.entity.enums.RoomStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomModel {

    private Long id;
    private Long hotelId;
    private Integer roomNumber;
    private String name;
    private RoomTypeModel roomType;
    private RoomStatus status;
}