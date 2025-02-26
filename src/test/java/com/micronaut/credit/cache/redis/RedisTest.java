package com.micronaut.credit.cache.redis;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisTest {

    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("redis:6.2"))
                    .withExposedPorts(6379);

    @BeforeAll
    static void startContainer() {
        REDIS_CONTAINER.start();
        System.setProperty("redis.uri", "redis://" + REDIS_CONTAINER.getHost() + ":" + REDIS_CONTAINER.getMappedPort(6379));
    }

    @AfterAll
    static void stopContainer() {
        REDIS_CONTAINER.stop();
    }

    @Test
    void testRedisConnection() {
        try (Jedis jedis = new Jedis(System.getProperty("redis.uri"))) {
            jedis.set("key", "value");
            String result = jedis.get("key");
            assertEquals("value", result);
        }
    }
}
