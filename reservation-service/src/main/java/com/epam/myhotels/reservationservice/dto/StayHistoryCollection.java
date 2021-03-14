package com.epam.myhotels.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayHistoryCollection {
    List<StayHistoryDto> stayHistories;
}