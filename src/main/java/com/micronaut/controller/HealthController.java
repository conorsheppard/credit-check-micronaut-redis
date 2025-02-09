package com.micronaut.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Controller("/health")
public class HealthController {
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    @Get(value = "/liveness", produces = MediaType.TEXT_PLAIN)
    public Mono<String> getLiveliness() {
        log.info("Received request to GET /liveness");
        return Mono.just("ok");
    }

    @Get(value = "/readiness", produces = MediaType.TEXT_PLAIN)
    public Mono<String> getReadiness() {
        log.info("Received request to GET /readiness");
        return Mono.just("ok");
    }
}