package com.epam.myhotels.hotels.repository;

import com.epam.myhotels.hotels.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}