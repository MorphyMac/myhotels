package com.epam.myhotels.hotels.dto;

import com.epam.myhotels.hotels.entity.enums.RoomStatus;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonRootName("room")
public class RoomDto {

    private Long id;
    @NotNull
    private Long hotelId;
    @NotNull
    private Integer roomNumber;
    @NotNull
    private String name;
    @NotNull
    private RoomTypeDto roomType;
    private RoomStatus status;
}