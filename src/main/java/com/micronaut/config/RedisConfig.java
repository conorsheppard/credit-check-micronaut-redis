package com.micronaut.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.inject.Singleton;

//@Singleton
public class RedisConfig {
//    private final RedisCommands<String, String> redisCommands;
//
//    public RedisConfig(RedisClient redisClient) {
//        StatefulRedisConnection<String, String> connection = redisClient.connect();
//        this.redisCommands = connection.sync();
//    }
//
//    @Singleton
//    public RedisCommands<String, String> redisCommands() {
//        return redisCommands;
//    }
}
