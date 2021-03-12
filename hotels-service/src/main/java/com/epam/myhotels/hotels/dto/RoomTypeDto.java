package com.epam.myhotels.hotels.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomTypeDto {

    private Long id;
    @NotNull
    @Size(min = 1)
    private Integer maxCapacity;
    private String description;
}