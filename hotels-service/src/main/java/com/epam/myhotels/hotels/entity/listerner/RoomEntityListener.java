package com.epam.myhotels.hotels.entity.listerner;

import com.epam.myhotels.hotels.entity.Room;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;

@Slf4j
public class RoomEntityListener {

    @PrePersist
    public void beforeSaving(Room room) {
        log.debug("room entity listener invoked.");
        if (room == null) throw new IllegalArgumentException("Room cannot be NULL while saving");
    }
}