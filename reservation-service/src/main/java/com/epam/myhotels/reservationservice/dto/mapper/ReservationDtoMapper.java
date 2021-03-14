package com.epam.myhotels.reservationservice.dto.mapper;

import com.epam.myhotels.reservationservice.dto.ReservationDto;
import com.epam.myhotels.reservationservice.model.ReservationModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    ReservationDto toDto(ReservationModel reservation);

    ReservationModel toModel(ReservationDto reservation);

    List<ReservationDto> toDto(List<ReservationModel> reservations);
}