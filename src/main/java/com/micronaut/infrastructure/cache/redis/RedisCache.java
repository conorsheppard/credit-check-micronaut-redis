package com.micronaut.infrastructure.cache.redis;

import com.micronaut.core.CreditScore;
import com.micronaut.core.Person;
import com.micronaut.infrastructure.cache.CreditScoreCache;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
@Primary
@Requires(bean = StatefulRedisConnection.class)
public class RedisCache implements CreditScoreCache {
    private final StatefulRedisConnection<String, String> redisConnection;
    private final long expirySeconds;

    public RedisCache(final StatefulRedisConnection<String, String> redisConnection,
                      @Property(name = "app.cache.expiry") long expirySeconds) {
        this.redisConnection = redisConnection;
        this.expirySeconds = expirySeconds;
    }

    @Override
    public Optional<CreditScore> get(String key) {
        var score = redisConnection.sync().get(key);
        return score == null ? Optional.empty() : Optional.of(new CreditScore(Person.create(key, null, null, null), Integer.parseInt(score), null));
    }

    @Override
    public void put(String key, CreditScore creditScore) {
        saveDataWithExpiry(key, String.valueOf(creditScore.getScore()), expirySeconds);
    }

    public void saveDataWithExpiry(String key, String value, long expiryInSeconds) {
        redisConnection.sync().setex(key, expiryInSeconds, value);
    }

    private void delete(String key) {
        redisConnection.sync().del(key);
    }
}
