package com.micronaut.core;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Introspected // Necessary for Micronaut's reflection capabilities
@Serdeable
public class FraudScore {

    private final Person person;
    private final int score;
    private final LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FraudScore that = (FraudScore) o;
        return score == that.score && person.equals(that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, score);
    }

    public static class Builder {
        private Person person;
        private int score;

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Builder score(int score) {
            this.score = score;
            return this;
        }

        public FraudScore build() {
            validatePerson();
            return new FraudScore(person, score, LocalDateTime.now(ZoneOffset.UTC));
        }

        private void validatePerson() {
            if (person == null) {
                throw new IllegalArgumentException("Person cannot be null.");
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
