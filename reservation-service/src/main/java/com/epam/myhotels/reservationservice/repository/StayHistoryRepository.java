package com.epam.myhotels.reservationservice.repository;

import com.epam.myhotels.reservationservice.entity.StayHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayHistoryRepository extends JpaRepository<StayHistory, Long> {
}