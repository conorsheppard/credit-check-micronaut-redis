package com.micronaut.controller;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@MicronautTest
public class HealthCheckControllerTest {
    @Inject
    private EmbeddedServer embeddedServer;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = embeddedServer.getURI().toString();
    }

    @Test
    public void testHealthCheck() {
        given()
                .when().get("/health")
                .then()
                .statusCode(200)
                .body(equalTo("ok"));
    }
}
