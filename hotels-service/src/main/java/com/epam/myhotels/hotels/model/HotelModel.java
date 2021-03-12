package com.epam.myhotels.hotels.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class HotelModel {

    private Long id;
    private String name;
    private AddressModel address;
    private String contact;
    private Set<RoomModel> rooms;
}