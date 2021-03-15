package com.epam.myhotels.hotels.controllers;

import com.epam.myhotels.hotels.dto.HotelDto;
import com.epam.myhotels.hotels.dto.HotelsDto;
import com.epam.myhotels.hotels.dto.mapper.HotelDtoMapper;
import com.epam.myhotels.hotels.services.HotelService;
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
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelDtoMapper hotelDtoMapper;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        HotelDto saved = hotelDtoMapper.toDto(hotelService.save(hotelDtoMapper.toModel(hotelDto)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId())
                                                  .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelByID(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(hotelDtoMapper.toDto(hotelService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable @Min(1) Long id, @RequestBody HotelDto hotel) {
        return ResponseEntity.ok(hotelDtoMapper.toDto(hotelService.update(id, hotelDtoMapper.toModel(hotel))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable @Min(1) Long id) {
        return hotelService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<HotelsDto> getAllHotels(@RequestParam(name = "city", required = false) String city) {
        return ResponseEntity.ok(new HotelsDto(hotelDtoMapper.toDto(hotelService.findAll(city))));
    }
}