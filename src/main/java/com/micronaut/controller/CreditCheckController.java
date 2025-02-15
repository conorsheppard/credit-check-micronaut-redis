package com.micronaut.controller;

import com.micronaut.entity.CreditScore;
import com.micronaut.service.CreditScoreService;
import com.micronaut.entity.Person;
import com.micronaut.request.CreditScoreRequest;
import com.micronaut.response.CreditScoreResponse;
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
public class CreditCheckController {
    @Inject
    private final CreditScoreService creditCheckService;
    // Feature flag toggle, default: deprecated
    private final String v1ApiStatus;
    private static final String V1_API_REMOVED = "removed";
    private static final String V1_API_DEPRECATED = "deprecated";
    private static final String V1_API_ENABLED = "enabled";

    public CreditCheckController(CreditScoreService creditCheckService,
                                 @Value("${feature.api.scores.v1.status:deprecated}") String v1ApiStatus) {
        this.creditCheckService = creditCheckService;
        this.v1ApiStatus = v1ApiStatus;
    }

    @Get(value = "/v1/scores", produces = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<CreditScoreResponse>> getCreditScoresV1(@RequestBean CreditScoreRequest creditScoreRequest) {
        Person person = creditScoreRequest.asPerson();
        Optional<CreditScore> creditScore = creditCheckService.calculateScoreForPerson(person);

        return switch (v1ApiStatus.toLowerCase()) {
            // ❌ removed → API endpoint is not available
            case V1_API_REMOVED -> Mono.just(HttpResponse.status(HttpStatus.GONE).body(null));

            // ⚠️ deprecated → API endpoint works but sends warning to the client
            case V1_API_DEPRECATED -> Mono
                    .justOrEmpty(creditScore)
                    .flatMap(score -> Mono.just(HttpResponse.ok(CreditScoreResponse.fromCreditScore(score))
                            .header("X-API-Warning", "Deprecated API version, use /api/v2/scores")))
                    .switchIfEmpty(Mono.just(HttpResponse.notFound()));

            // ✅ enabled → API endpoint is active
            default -> Mono
                    .justOrEmpty(creditScore)
                    .flatMap(score -> Mono.just(HttpResponse.ok(CreditScoreResponse.fromCreditScore(score))))
                    .switchIfEmpty(Mono.error(new HttpStatusException(HttpStatus.NOT_FOUND, "Credit score not found")));
        };
    }

    @Post(value = "/v2/scores")
    public Mono<CreditScoreResponse> getCreditScoresV2(@Body CreditScoreRequest creditScoreRequest) {
        Person person = creditScoreRequest.asPerson();
        Optional<CreditScore> creditScore = creditCheckService.calculateScoreForPerson(person);

        return Mono.justOrEmpty(creditScore)
                .map(CreditScoreResponse::fromCreditScore)
                .switchIfEmpty(Mono.error(new HttpStatusException(HttpStatus.NOT_FOUND, "Credit score not found")));
    }
}
