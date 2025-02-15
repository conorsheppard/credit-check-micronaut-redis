package com.micronaut.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Introspected
@Serdeable
public class CreditScore {
    @JsonProperty("person")
    private final Person person;
    @JsonProperty("score")
    private final int score;
}
