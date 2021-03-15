package com.epam.myhotels.hotels.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class HotelDto {

    private Long id;
    @NotNull
    private String name;
    private AddressDto address;
    @NotNull
    private String contact;
    private Set<RoomDto> rooms;
}