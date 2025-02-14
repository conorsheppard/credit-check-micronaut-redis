package com.micronaut.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@Introspected
@Serdeable
public class CreditScore {
    private final Person person;
    private final int score;
    private final LocalDateTime time;
}
