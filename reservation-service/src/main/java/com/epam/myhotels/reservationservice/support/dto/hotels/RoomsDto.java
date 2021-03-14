package com.epam.myhotels.reservationservice.support.dto.hotels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsDto {
    List<RoomDto> rooms;
}