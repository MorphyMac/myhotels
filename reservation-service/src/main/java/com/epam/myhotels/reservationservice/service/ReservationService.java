package com.epam.myhotels.reservationservice.service;


import com.epam.myhotels.reservationservice.entity.Reservation;
import com.epam.myhotels.reservationservice.exception.ReservationNotFoundException;
import com.epam.myhotels.reservationservice.model.ReservationModel;
import com.epam.myhotels.reservationservice.model.mapper.ReservationModelMapper;
import com.epam.myhotels.reservationservice.repository.ReservationRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Setter
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationModelMapper reservationModelMapper;

    @Transactional
    public ReservationModel save(ReservationModel reservationModel) {
        return reservationModelMapper
                .toModel(reservationRepository.save(reservationModelMapper.toEntity(reservationModel)));
    }

    @Transactional(readOnly = true)
    public ReservationModel findById(Long id) {
        return reservationRepository.findById(id).map(reservation -> reservationModelMapper.toModel(reservation))
                                    .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    @Transactional
    public ReservationModel update(Long id, ReservationModel reservation) {
        return reservationRepository.findById(id).map(existingReservation -> {
            reservation.setId(existingReservation.getId());
            Reservation update = reservationModelMapper.toEntity(reservation);
            return reservationModelMapper.toModel(reservationRepository.save(update));
        }).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    @Transactional
    public boolean delete(Long id) {
        return reservationRepository.findById(id).map(existingReservation -> {
            reservationRepository.delete(existingReservation);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<ReservationModel> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (CollectionUtils.isEmpty(reservations)) {
            return Collections.emptyList();
        } else {
            return reservationModelMapper.toModels(reservations);
        }
    }
}