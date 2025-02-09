package com.micronaut.controller;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreService;
import com.micronaut.core.Person;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Controller("/api")
@Introspected
@Slf4j
public class FraudScoreController {
    private final FraudScoreService fraudScoreService;

    public FraudScoreController(FraudScoreService fraudScoreService) {
        this.fraudScoreService = fraudScoreService;
    }

    @Get(value = "/v1/scores", produces = MediaType.APPLICATION_JSON)
    public Mono<FraudScoreResponse> getScoresV1(@RequestBean FraudScoreQuery fraudScoreQuery) {
        Person person = fraudScoreQuery.asPerson();
        Optional<FraudScore> fraudScore = fraudScoreService.calculateScoreForPerson(person);

        return Mono.justOrEmpty(fraudScore)
                .map(FraudScoreResponse::fromFraudScore)
                .switchIfEmpty(Mono.error(new HttpStatusException(HttpStatus.NOT_FOUND, "Fraud score not found")));
    }

    @Post(value = "/v2/scores")
    public Mono<FraudScoreResponse> getScoresV2(@Body FraudScoreQuery fraudScoreQuery) {
        Person person = fraudScoreQuery.asPerson();
        Optional<FraudScore> fraudScore = fraudScoreService.calculateScoreForPerson(person);

        return Mono.justOrEmpty(fraudScore)
                .map(FraudScoreResponse::fromFraudScore)
                .switchIfEmpty(Mono.error(new HttpStatusException(HttpStatus.NOT_FOUND, "Fraud score not found")));
    }


}
