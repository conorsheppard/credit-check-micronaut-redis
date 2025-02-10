package com.micronaut.controller;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreService;
import com.micronaut.core.Person;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Controller("/api")
@Introspected
@Slf4j
public class FraudScoreController {
    @Inject
    private final FraudScoreService fraudScoreService;
    // Feature flag toggle, default: deprecated
    private final String v1ApiStatus;
    private static final String V1_API_REMOVED = "removed";
    private static final String V1_API_DEPRECATED = "deprecated";
    private static final String V1_API_ENABLED = "enabled";

    public FraudScoreController(FraudScoreService fraudScoreService,
                                @Value("${feature.api.scores.v1.status:deprecated}") String v1ApiStatus) {
        this.fraudScoreService = fraudScoreService;
        this.v1ApiStatus = v1ApiStatus;
    }

    @Get(value = "/v1/scores", produces = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<FraudScoreResponse>> getScoresV1(@RequestBean FraudScoreQuery fraudScoreQuery) {
        Person person = fraudScoreQuery.asPerson();
        Optional<FraudScore> fraudScore = fraudScoreService.calculateScoreForPerson(person);

        return switch (v1ApiStatus.toLowerCase()) {
            // ❌ removed → API endpoint is not available
            case V1_API_REMOVED -> Mono.just(HttpResponse.status(HttpStatus.GONE).body(null));

            // ⚠️ deprecated → API endpoint works but sends warning to the client
            case V1_API_DEPRECATED -> Mono
                    .justOrEmpty(fraudScore)
                    .flatMap(score -> Mono.just(HttpResponse.ok(FraudScoreResponse.fromFraudScore(score))
                            .header("X-API-Warning", "Deprecated API version, use /api/v2/scores")))
                    .switchIfEmpty(Mono.just(HttpResponse.notFound()));

            // ✅ enabled → API endpoint is active
            default -> Mono
                    .justOrEmpty(fraudScore)
                    .flatMap(score -> Mono.just(HttpResponse.ok(FraudScoreResponse.fromFraudScore(score))))
                    .switchIfEmpty(Mono.error(new HttpStatusException(HttpStatus.NOT_FOUND, "Fraud score not found")));
        };
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
