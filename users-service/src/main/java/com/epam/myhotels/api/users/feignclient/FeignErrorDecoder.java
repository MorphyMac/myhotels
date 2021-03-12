package com.epam.myhotels.api.users.feignclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    private Environment environment;

    @Autowired
    public FeignErrorDecoder(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                log.error("Bad request feign. methodKey : {}, reason: {}", methodKey, response.reason());
                break;
            case 404:
                log.error("Not found feign exception. methodKey : {}, reason: {}", methodKey, response.reason());
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
            default:
                log.error("Feign generic exception. methodKey : {}, reason: {}, status = {}, body = {}", methodKey,
                        response
                        .reason(), response.status(), response.body());
                return new Exception(response.reason());
        }

        return null;
    }
}