package com.micronaut.infrastructure.cache;

import com.micronaut.core.FraudScore;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.cache.hashmap.SynchronizedLRUCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedLRUCacheTest {

    @Test
    public void testPut() {
        FraudScoreCache fraudScoreCache = new SynchronizedLRUCache(2);
        fraudScoreCache.put("key", new FraudScore(Person.create(
                "email@email.com",
                "name",
                "surname",
                "NW1 8AB"),
                1, null));

        assertEquals(1, fraudScoreCache.get("key")
                .map(FraudScore::getScore).orElse(-1).intValue());
    }
}
