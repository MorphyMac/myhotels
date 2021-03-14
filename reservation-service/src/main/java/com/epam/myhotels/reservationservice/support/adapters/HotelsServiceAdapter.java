package com.epam.myhotels.reservationservice.support.adapters;

import com.epam.myhotels.reservationservice.support.dto.hotels.HotelDto;
import com.epam.myhotels.reservationservice.support.dto.hotels.HotelsDto;
import com.epam.myhotels.reservationservice.support.dto.hotels.RoomDto;
import com.epam.myhotels.reservationservice.support.dto.hotels.RoomsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${api.hotels.service.name}")
public interface HotelsServiceAdapter {

    @GetMapping(value = "/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HotelsDto> getHotels();

    @GetMapping(value = "/hotels/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HotelDto> getHotelById(@PathVariable("hotelId") Long hotelId);

    @GetMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomsDto> getRooms();

    @GetMapping(value = "/rooms/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDto> getRoomById(@PathVariable("roomId") Long roomId);
}