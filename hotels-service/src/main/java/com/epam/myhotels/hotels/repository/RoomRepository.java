package com.epam.myhotels.hotels.repository;

import com.epam.myhotels.hotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, Long roomNumber);
}