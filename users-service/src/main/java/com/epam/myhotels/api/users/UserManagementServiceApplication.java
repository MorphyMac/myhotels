package com.epam.myhotels.api.users;

import com.epam.myhotels.api.users.feignclient.HotelsServiceClient;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserManagementServiceApplication {
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

    @Autowired
    private HotelsServiceClient hotelsServiceClient;

    @GetMapping("/users/status")
    public String status() {
        log.debug("log with sleuth+zipkin setup.. Before calling guest service.");
        String status = hotelsServiceClient.getStatus();
        log.debug("hotels-service status = {}" + status);
        log.debug("log with sleuth+zipkin setup.. After calling guest service.");

        return "Users service is working on PORT = " + environment.getProperty("local.server.port");
    }
}