package com.epam.myhotels.reservationservice.model.mapper;

import com.epam.myhotels.reservationservice.entity.Reservation;
import com.epam.myhotels.reservationservice.model.ReservationModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationModelMapper {

    ReservationModel toModel(Reservation reservation);

    Reservation toEntity(ReservationModel reservationModel);

    List<ReservationModel> toModels(List<Reservation> reservations);
}