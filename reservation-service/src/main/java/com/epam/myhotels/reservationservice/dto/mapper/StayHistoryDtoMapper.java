package com.epam.myhotels.reservationservice.dto.mapper;

import com.epam.myhotels.reservationservice.dto.StayHistoryDto;
import com.epam.myhotels.reservationservice.model.StayHistoryModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReservationDtoMapper.class})
public interface StayHistoryDtoMapper {

    StayHistoryDto toDto(StayHistoryModel stayHistory);

    StayHistoryModel toModel(StayHistoryDto stayHistory);

    List<StayHistoryDto> toDto(List<StayHistoryModel> stayHistories);
}