package com.epam.myhotels.reservationservice.controller;

import com.epam.myhotels.reservationservice.controller.swagger.ReservationSwaggerWrapper;
import com.epam.myhotels.reservationservice.dto.ReservationDto;
import com.epam.myhotels.reservationservice.dto.mapper.ReservationDtoMapper;
import com.epam.myhotels.reservationservice.exception.ReservationNotFoundException;
import com.epam.myhotels.reservationservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController implements ReservationSwaggerWrapper {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationDtoMapper reservationDtoMapper;

    @GetMapping("/{reservationId}")
    @Override
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long reservationId)
            throws ReservationNotFoundException {
        return ResponseEntity.ok(reservationDtoMapper.toDto(reservationService.findById(reservationId)));
    }

    @PostMapping
    @Override
    public ResponseEntity<ReservationDto> reservationRequest(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto saved = reservationDtoMapper
                .toDto(reservationService.save(reservationDtoMapper.toModel(reservationDto)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{reservationId}")
                                                  .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{reservationId}")
    @Override
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable @Min(1) Long reservationId,
                                                            @RequestBody ReservationDto reservationDto)
            throws ReservationNotFoundException {
        return ResponseEntity.ok(reservationDtoMapper
                .toDto(reservationService.update(reservationId, reservationDtoMapper.toModel(reservationDto))));
    }

    @GetMapping("/guests/{guestId}")
    @Override
    public ResponseEntity<ReservationDto> getReservationsByGuestId(@PathVariable Long guestId)
            throws ReservationNotFoundException {
        return null;
    }
}