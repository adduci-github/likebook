package com.likebook.config.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfig {
    private RedisServer redisServer;

    @PostConstruct
    public void redisServerStart() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @PreDestroy
    public void redisServerStop() {
        redisServer.stop();
    }
}
