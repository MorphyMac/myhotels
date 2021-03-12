package com.epam.myhotels.hotels.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomTypeModel {

    private Long id;
    private Integer maxCapacity;
    private String description;
}