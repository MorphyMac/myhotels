package com.epam.myhotels.hotels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthStatusController {
    @Autowired
    private Environment environment;

    @GetMapping("/hotels/status")
    public String status() {
        return "Hotels service is working on PORT = " + environment.getProperty("local.server.port");
    }
}