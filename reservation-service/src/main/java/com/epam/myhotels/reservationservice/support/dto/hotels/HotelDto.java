package com.epam.myhotels.reservationservice.support.dto.hotels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class HotelDto {

    private Long id;
    private String name;
    private AddressDto address;
    private String contact;
    private Set<RoomDto> rooms;
}