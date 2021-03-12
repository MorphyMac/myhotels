package com.epam.myhotels.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyPreFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.debug("Pre-filter is executed");
        ServerHttpRequest request = exchange.getRequest();

        String requestPath = request.getPath().toString();
        logger.debug("Request path = {}", requestPath);

        HttpHeaders headers = request.getHeaders();
        headers.keySet()
                .parallelStream()
                .forEach(headerKey -> logger.debug("Request header = {}", headers.get(headerKey)));

        return chain.filter(exchange);
    }
}