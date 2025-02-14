package com.micronaut.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Controller("/health")
public class HealthController {
    @Get(produces = MediaType.TEXT_PLAIN)
    public Mono<HttpResponse<String>> getHealth() {
        log.info("Received request to GET /health");
        return Mono.just(HttpResponse.ok("ok"));
    }
}