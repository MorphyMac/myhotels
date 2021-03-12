package com.epam.myhotels.hotels.services;


import com.epam.myhotels.hotels.entity.Room;
import com.epam.myhotels.hotels.exception.RoomNotFoundException;
import com.epam.myhotels.hotels.model.RoomModel;
import com.epam.myhotels.hotels.model.mapper.RoomModelMapper;
import com.epam.myhotels.hotels.repository.RoomRepository;
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
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomModelMapper roomModelMapper;

    @Transactional
    public RoomModel save(RoomModel roomModel) {
        return roomModelMapper.toModel(roomRepository.save(roomModelMapper.toEntity(roomModel)));
    }

    @Transactional(readOnly = true)
    public RoomModel findById(Long id) {
        return roomRepository.findById(id).map(room -> roomModelMapper.toModel(room))
                             .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    @Transactional
    public RoomModel update(Long id, RoomModel room) {
        return roomRepository.findById(id).map(existingRoom -> {
            Room update = roomModelMapper.toEntity(room);
            update.setId(existingRoom.getId());
            return roomModelMapper.toModel(roomRepository.save(update));
        }).orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    @Transactional
    public boolean delete(Long id) {
        return roomRepository.findById(id).map(existingRoom -> {
            roomRepository.delete(existingRoom);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<RoomModel> findAll() {
        List<Room> rooms = roomRepository.findAll();
        if (CollectionUtils.isEmpty(rooms)) {
            return Collections.emptyList();
        } else {
            return roomModelMapper.toModels(rooms);
        }
    }
}