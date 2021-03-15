package com.epam.myhotels.reservationservice.controller.swagger;

import com.epam.myhotels.reservationservice.controller.exceptionhandling.ApplicationErrorDto;
import com.epam.myhotels.reservationservice.dto.ReservationCollectionDto;
import com.epam.myhotels.reservationservice.dto.ReservationDto;
import com.epam.myhotels.reservationservice.exception.ReservationNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller APIs to make, update and retrieve reservation information.
 */
@Api("Controller APIs to make, update and retrieve reservation information.")
public interface ReservationSwaggerWrapper {
    // @formatter:off

    @ApiOperation(value = "To get reservation info by id.", produces = APPLICATION_JSON_VALUE, response = ReservationDto.class)
    @ApiResponses({
             @ApiResponse(code = 200, message = "Reservation fetched successfully.", response = ReservationDto.class),
             @ApiResponse(code = 403, message = "Not authorized to do this operation", response = ApplicationErrorDto.class),
             @ApiResponse(code = 404, message = "Entity not found in Reservation/downstream services.", response =ApplicationErrorDto.class),
             @ApiResponse(code = 500, message = "Unwanted exception from this/downstream service.", response = ApplicationErrorDto.class)
    })
    ResponseEntity<ReservationDto> getReservation(Long reservationId) throws ReservationNotFoundException;

    @ApiOperation(value = "Create new reservation.", consumes = APPLICATION_JSON_VALUE, response = ReservationDto.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Reservation created successfully.", response = ReservationDto.class),
            @ApiResponse(code = 403, message = "Not authorized to do this operation", response = ApplicationErrorDto.class),
            @ApiResponse(code = 404, message = "Entity not found in Reservation/downstream services.", response = ApplicationErrorDto.class),
            @ApiResponse(code = 500, message = "Unwanted exception from this/downstream service.", response = ApplicationErrorDto.class)
    })
    ResponseEntity<ReservationDto> reservationRequest(ReservationDto reservation);

    @ApiOperation(value = "Update reservation.", consumes = APPLICATION_JSON_VALUE, response = ReservationDto.class)
    @ApiResponses({
            @ApiResponse(code = 202, message = "Reservation updated successfully.", response = ReservationDto.class),
            @ApiResponse(code = 403, message = "Not authorized to do this operation", response = ApplicationErrorDto.class),
            @ApiResponse(code = 404, message = "Entity not found in Reservation/from downstream services.", response = ApplicationErrorDto.class),
            @ApiResponse(code = 500,message = "Unwanted exception from this/downstream service.", response = ApplicationErrorDto.class)
    })
    ResponseEntity<ReservationDto> updateReservation(Long reservationId, ReservationDto reservation) throws ReservationNotFoundException;

    @ApiOperation(value = "Get reservation history for Guest", produces = APPLICATION_JSON_VALUE, response =ReservationCollectionDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservation information fetched successfully.", response =ReservationCollectionDto.class),
            @ApiResponse(code = 403, message = "Not authorized to do this operation",response = ApplicationErrorDto.class),
            @ApiResponse(code = 404, message = "Entity not found in Reservation/downstream services.", response = ApplicationErrorDto.class),
            @ApiResponse(code = 500, message = "Unwanted exception from this/downstream service.", response =ApplicationErrorDto.class)
    })
    ResponseEntity<ReservationCollectionDto> getReservationsByGuestId(String guestId) throws ReservationNotFoundException;

    // @formatter:on
}