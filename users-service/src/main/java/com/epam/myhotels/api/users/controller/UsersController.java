package com.epam.myhotels.api.users.controller;

import com.epam.myhotels.api.users.dto.UserDto;
import com.epam.myhotels.api.users.dto.UserRegistrationDto;
import com.epam.myhotels.api.users.dto.UserValidationGroup;
import com.epam.myhotels.api.users.dtomapper.UserDtoMapper;
import com.epam.myhotels.api.users.model.UserModel;
import com.epam.myhotels.api.users.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Slf4j
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private Environment environment;

    @PostMapping("/users")
    public ResponseEntity<Object> create(@Validated(UserValidationGroup.CreateUser.class) @RequestBody UserRegistrationDto registrationDto)
            throws URISyntaxException {
        UserModel user = userService.createUser(userDtoMapper.toModel(registrationDto));

        /*        1. with gateway info
        String location = String.join("/", environment.getProperty("api.gateway.url"), environment
                .getProperty("spring.application.name"), "users", user.getUserId());*//*
        return ResponseEntity.created(new URI(location)).build();
        */

        // 2. we can use X-Forwarded-* headers for getting api-gateway location

        // 3. we can create URI from current request
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
                                                  .buildAndExpand(user.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userDtoMapper.toDto(userService.findByUserId(userId)));
    }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> delete(@PathVariable String userId) {
        return userService.deleteUser(userId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable String userId,
                                          @Validated(UserValidationGroup.UpdateUser.class) @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userDtoMapper.toDto(userService.update(userId, userDtoMapper.toModel(userDto))));
    }


    @HystrixCommand(fallbackMethod = "getUserHystrixSimulationFallback", commandProperties = {@HystrixProperty(name =
            "execution" + ".isolation.thread.timeoutInMilliseconds", value = "1000")})
    @GetMapping("/users/hystrix-simulation")
    public ResponseEntity<UserDto> getUserHystrixSimulation() {
        throw new RuntimeException("Intentionally throwing to simulate hystrix working");
    }

    private ResponseEntity<UserDto> getUserHystrixSimulationFallback() {
        log.debug("Hystrix getUserByIdFallback called");
        return ResponseEntity.ok(new UserDto());
    }
}