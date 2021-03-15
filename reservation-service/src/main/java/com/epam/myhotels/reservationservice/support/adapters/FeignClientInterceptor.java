package com.epam.myhotels.reservationservice.support.adapters;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("Feign clients interceptor called");
    }
}