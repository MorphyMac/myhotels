package com.epam.myhotels.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableEurekaClient
public class ReservationsServiceApplication {
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ReservationsServiceApplication.class, args);
    }

    @GetMapping("/reservations/status")
    public String status() {
        return "Reservations service is working on PORT = " + environment.getProperty("local.server.port");
    }
}