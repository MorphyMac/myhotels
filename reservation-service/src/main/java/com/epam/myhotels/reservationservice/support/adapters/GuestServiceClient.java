package com.epam.myhotels.reservationservice.support.adapters;

import com.epam.myhotels.reservationservice.support.dto.users.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${api.users.service.name}")
public interface GuestServiceClient {

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId);
}