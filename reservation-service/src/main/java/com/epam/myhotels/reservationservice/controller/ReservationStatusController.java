package com.epam.myhotels.reservationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationStatusController {

    @Autowired
    private Environment environment;

    @GetMapping("/reservations/status")
    public String status() {
        return "Reservations service is working on PORT = " + environment.getProperty("local.server.port");
    }
}