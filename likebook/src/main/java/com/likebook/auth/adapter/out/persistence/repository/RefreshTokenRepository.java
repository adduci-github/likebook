package com.likebook.auth.adapter.out.persistence.repository;

import com.likebook.auth.adapter.out.persistence.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final ReactiveStringRedisTemplate redisTemplate;

    public Mono<Boolean> save(RefreshTokenEntity refreshToken) {
        return redisTemplate.opsForValue()
                .set(refreshToken.getRefreshToken(), refreshToken.getUsername())
                .filter(isSet -> isSet)
                .flatMap(isSet -> redisTemplate.expire(refreshToken.getRefreshToken(), Duration.ofSeconds(120L)));
    }

    public Mono<RefreshTokenEntity> findByToken(String refreshToken) {
        return redisTemplate.opsForValue()
                .get(refreshToken)
                .map(username -> new RefreshTokenEntity(refreshToken, username));
    }
}
