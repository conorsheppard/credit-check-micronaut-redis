package com.micronaut.infrastructure.cache;

import com.micronaut.core.FraudScore;
import com.micronaut.core.FraudScoreRepository;
import com.micronaut.core.Person;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
@Primary
public class FraudScoreCache implements FraudScoreRepository {

    private static final int MAX_CACHE_SIZE = 2;
    private final Map<String, FraudScore> cachedScores = Collections.synchronizedMap(new LinkedHashMap<>(MAX_CACHE_SIZE + 1, .75F, true) {
        @Override
        public boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    });

    @Override
    public Optional<FraudScore> getScore(Person person) {
        // check for stale cache entry
//        cachedScores.get(getCacheKey(person))
        return Optional.ofNullable(cachedScores.get(getCacheKey(person)));
    }

    @Override
    public void saveScore(FraudScore score) {
        cachedScores.put(getCacheKey(score.getPerson()), score);
    }

    private String getCacheKey(Person person) {
        return person.getEmail();
    }
}
