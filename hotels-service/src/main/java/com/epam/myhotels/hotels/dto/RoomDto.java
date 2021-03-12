package com.epam.myhotels.hotels.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

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
    private BigInteger rate;
    @NotNull
    private RoomTypeDto roomType;
}