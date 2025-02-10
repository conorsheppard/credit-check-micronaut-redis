package com.micronaut.infrastructure.cache.redis;

import com.micronaut.core.FraudScore;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.cache.FraudScoreCache;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
@Primary
public class RedisCache implements FraudScoreCache {
    private final RedisCommands<String, String> redisCommands;
    private final long expirySeconds;

    @Inject
    public RedisCache(RedisCommands<String, String> redisCommands, @Property(name = "app.cache.expiry") long expirySeconds) {
        this.redisCommands = redisCommands;
        this.expirySeconds = expirySeconds;
    }

    @Override
    public Optional<FraudScore> get(String key) {
        var email = redisCommands.get(key);
        return email == null ? Optional.empty() : Optional.of(new FraudScore(Person.create(email, null, null, null), 0, null));
    }

    @Override
    public void put(String key, FraudScore fraudScore) {
        saveDataWithExpiry(key, fraudScore.toString(), expirySeconds);
    }

    public void saveDataWithExpiry(String key, String value, long expiryInSeconds) {
        redisCommands.setex(key, expiryInSeconds, value);
    }

    public void setExpiry(String key, int seconds) {
        redisCommands.setex(key, seconds, "");
        redisCommands.expire(key, seconds);
    }

    private void delete(String key) {
        redisCommands.del(key);
    }
}
