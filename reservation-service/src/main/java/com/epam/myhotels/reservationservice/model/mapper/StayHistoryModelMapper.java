package com.epam.myhotels.reservationservice.model.mapper;

import com.epam.myhotels.reservationservice.entity.StayHistory;
import com.epam.myhotels.reservationservice.model.StayHistoryModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReservationModelMapper.class})
public interface StayHistoryModelMapper {

    StayHistoryModel toModel(StayHistory stayHistory);

    StayHistory toEntity(StayHistoryModel stayHistory);

    List<StayHistoryModel> toModels(List<StayHistory> stayHistories);
}