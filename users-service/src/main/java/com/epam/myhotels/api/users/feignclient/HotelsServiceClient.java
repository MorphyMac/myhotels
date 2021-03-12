package com.epam.myhotels.api.users.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${app.eureka.hotels.service.name}", fallback = HotelsServiceFallback.class)
public interface HotelsServiceClient {

    @GetMapping("/hotels/status")
    String getStatus();
}

class HotelsServiceFallback implements HotelsServiceClient {
    @Override
    public String getStatus() {
        return "Hotels service is not working. Please try again...";
    }
}