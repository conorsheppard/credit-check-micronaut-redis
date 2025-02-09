package com.micronaut.controller;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreService;
import com.micronaut.core.Person;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Optional;

@Controller("/api")
public class FraudScoreController {

    private final FraudScoreService fraudScoreService;

    public FraudScoreController(FraudScoreService fraudScoreService) {
        this.fraudScoreService = fraudScoreService;
    }

    @Get(value = "/v1/scores", produces = MediaType.APPLICATION_JSON)
    public Mono<FraudScoreResponse> getScores(@RequestBean FraudScoreQuery fraudScoreQuery) {
        Person person = fraudScoreQuery.asPerson();
        Optional<FraudScore> fraudScore = fraudScoreService.calculateScoreForPerson(person);
        return Mono.just(fraudScore.map(FraudScoreResponse::fromFraudScore).orElse(new FraudScoreResponse()));
    }

    @Get(value = "/v2/scores", produces = MediaType.APPLICATION_JSON)
    public Mono<FraudScoreResponse> getScoresV2(@RequestBean FraudScoreQuery fraudScoreQuery) {
        Person person = fraudScoreQuery.asPerson();
        Optional<FraudScore> fraudScore = fraudScoreService.calculateScoreForPerson(person);
        return Mono.justOrEmpty(fraudScore).map(FraudScoreResponse::fromFraudScore);
    }
}
