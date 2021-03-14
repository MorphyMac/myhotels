package com.epam.myhotels.reservationservice.exception;

public class StayHistoryNotFoundException extends RuntimeException {
    public StayHistoryNotFoundException(String message) {
        super(message);
    }
}