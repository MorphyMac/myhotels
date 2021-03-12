package com.epam.myhotels.hotels.controllers;

import com.epam.myhotels.hotels.dto.RoomDto;
import com.epam.myhotels.hotels.dto.RoomsDto;
import com.epam.myhotels.hotels.dto.mapper.RoomDtoMapper;
import com.epam.myhotels.hotels.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomDtoMapper roomDtoMapper;

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        RoomDto saved = roomDtoMapper.toDto(roomService.save(roomDtoMapper.toModel(roomDto)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId())
                                                  .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomByID(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(roomDtoMapper.toDto(roomService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable @Min(1) Long id, @RequestBody RoomDto room) {
        return ResponseEntity.ok(roomDtoMapper.toDto(roomService.update(id, roomDtoMapper.toModel(room))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable @Min(1) Long id) {
        return roomService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<RoomsDto> getAllRooms() {
        return ResponseEntity.ok(new RoomsDto(roomDtoMapper.toDto(roomService.findAll())));
    }

}