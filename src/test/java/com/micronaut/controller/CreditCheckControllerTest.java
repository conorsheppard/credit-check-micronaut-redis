package com.micronaut.controller;

import io.micronaut.cache.SyncCache;
import io.micronaut.context.annotation.Property;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
@MicronautTest(rebuildContext = true)
class CreditCheckControllerTest {
    private final String apiStatusProperty = "feature.api.scores.v1.status";
    private final String v1Endpoint = "/api/v1/scores?firstName=John&lastName=Doe&postCode=NW18AB&email=email@example.com";
    private final String v2Endpoint = "/api/v2/scores";

    @Inject
    private EmbeddedServer embeddedServer;

//    @Inject
//    SyncCache myRedisCache;

    @BeforeAll
    static void startRedis() {
        redisContainer.start();
        System.setProperty("redis.uri", "redis://" + redisContainer.getHost() + ":" + redisContainer.getMappedPort(6379));
    }

    @AfterAll
    static void stopRedis() {
        redisContainer.stop();
    }

    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>(DockerImageName.parse("redis:6.2"))
                    .withExposedPorts(6379);

    private final String requestBody = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "postCode": "NW!8AB",
                "email": "email@example.com"
            }
            """;

    private final String requestBodyBadEmailFormat = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "postCode": "NW!8AB",
                "email": "email_example.com"
            }
            """;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = embeddedServer.getURI().toString();
    }

    @Test
    @Property(name = apiStatusProperty, value = "removed")
    void testV1ApiRemoved() {
        given()
                .when().get(v1Endpoint)
                .then()
                .statusCode(410)
                .body(equalTo(""));
    }

    @Test
    @Property(name = apiStatusProperty, value = "deprecated")
    void testV1ApiDeprecated() {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().get(v1Endpoint)
                .then()
                .statusCode(200)
                .header("X-API-Warning", "Deprecated API version, use /api/v2/scores");
    }

    @Test
    @Property(name = apiStatusProperty, value = "enabled")
    void testV1ApiEnabled() {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().get(v1Endpoint)
                .then()
                .statusCode(200);
    }

    @Test
    void testV2ApiCreditScoreFound() {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(v2Endpoint)
                .then()
                .statusCode(200);
    }

    @Test
    void testV2ApiBadEmailFormat() {
        given()
                .contentType(ContentType.JSON)
                .body(requestBodyBadEmailFormat)
                .when().post(v2Endpoint)
                .then()
                .statusCode(500);
    }
}
