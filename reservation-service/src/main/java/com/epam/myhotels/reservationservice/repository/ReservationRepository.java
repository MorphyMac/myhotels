package com.epam.myhotels.reservationservice.repository;

import com.epam.myhotels.reservationservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuestId(String guestId);
}