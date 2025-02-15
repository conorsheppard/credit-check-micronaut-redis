package com.micronaut.cache.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micronaut.entity.CreditScore;
import com.micronaut.entity.Person;
import com.micronaut.cache.CreditScoreCache;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;

import java.util.Optional;

@Singleton
@Primary
public class RedisCache implements CreditScoreCache {
    private final StatefulRedisConnection<String, String> redisConnection;
    private final long expirySeconds;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisCache(final StatefulRedisConnection<String, String> redisConnection,
                      @Property(name = "app.cache.expiry") long expirySeconds) {
        this.redisConnection = redisConnection;
        this.expirySeconds = expirySeconds;
    }

    @Override
    @SneakyThrows
    public Optional<CreditScore> get(String key) {
        String creditScoreJson = redisConnection.sync().get(key);
        return creditScoreJson == null ? Optional.empty() : Optional.of(objectMapper.readValue(creditScoreJson, CreditScore.class));
    }

    @Override
    @SneakyThrows
    public void put(String key, CreditScore creditScore) {
        String creditScoreMapped = objectMapper.writeValueAsString(creditScore);
        saveDataWithExpiry(key, creditScoreMapped, expirySeconds);
    }

    public void saveDataWithExpiry(String key, String value, long expiryInSeconds) {
        redisConnection.sync().setex(key, expiryInSeconds, value);
    }
}
