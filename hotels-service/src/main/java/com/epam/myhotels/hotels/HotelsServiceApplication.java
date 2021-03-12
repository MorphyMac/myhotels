package com.epam.myhotels.hotels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@EnableHystrix
public class HotelsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelsServiceApplication.class, args);
    }

    @Autowired
    private Environment environment;

    @GetMapping("/hotels/status")
    public String status() {
        return "Hotels service is working on PORT = " + environment.getProperty("local.server.port");
    }

}