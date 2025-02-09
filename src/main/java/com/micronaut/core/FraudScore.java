package com.micronaut.core;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Data
public class FraudScore {

    private final Person person;
    private final int score;
    private final LocalDateTime time;

    FraudScore(Person person, int score, LocalDateTime time) {
        this.person = person;
        this.score = score;
        this.time = time;
    }

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
