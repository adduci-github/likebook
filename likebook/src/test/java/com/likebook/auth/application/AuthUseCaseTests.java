package com.likebook.auth.application;

import com.likebook.auth.domain.Token;
import com.likebook.auth.application.port.in.AuthPort;
import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.application.port.in.dto.AuthResponse;
import com.likebook.auth.application.port.out.JWTAuthPort;
import com.likebook.auth.application.port.out.UserPersistencePort;
import com.likebook.auth.domain.Auth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthUseCaseTests {
    private final UserPersistencePort userPersistencePort = Mockito.mock(UserPersistencePort.class);
    private final JWTAuthPort jwtAuthPort = Mockito.mock(JWTAuthPort.class);

    private final AuthRequest existsUser = AuthRequest.builder().username("test").password("test").build();
    private final AuthRequest notExistsUser = AuthRequest.builder().username("test2").password("test2").build();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String exampleAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjc4MTU4MDY0LCJleHAiOjE2NzgxODY4NjR9.yNhy_pFkMjsr4hD_Y5yHmOpvMM7QzzCj8baBEEna9HgN7wcmY5ImuBxXTjFE-PGeDHSOmLZGFlV52_RsS8Gg2g";
    private final String exampleRefreshToken = "eyJhbGciOiJIUzUxMiJ8.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjc4MTU4MDY0LCJleHAiOjE2NzgxODY4NjR9.yNhy_pFkMjsr4hD_Y5yHmOpvMM7QzzCj8baBEEna9HgN7wcmY5ImuBxXTjFE-PGeDHSOmLZGFlV52_RsS8Gg2g";

    @BeforeEach
    public void initUserPersistencePort() {
        when(userPersistencePort.findByUsername(existsUser))
                .thenReturn(
                        Mono.just(
                                Auth.builder()
                                        .username(existsUser.getUsername())
                                        .password(passwordEncoder.encode(existsUser.getPassword()))
                                        .build()
                        )
                );

        when(userPersistencePort.findByUsername(notExistsUser))
                .thenReturn(
                        Mono.empty()
                );
    }

    @BeforeEach
    public void initJWTAuthPort() {
        when(jwtAuthPort.generateToken(existsUser.getUsername()))
                .thenReturn(Mono.just(Token.builder().accessToken(exampleAccessToken).refreshToken(exampleRefreshToken).build()));
    }

    @Test
    public void if_not_exists_user_then_empty() {
        AuthPort authPort = new AuthUseCase(userPersistencePort, jwtAuthPort);

        Mono<AuthResponse> response = authPort.auth(notExistsUser);

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void if_exists_user_then_return_token() {
        AuthPort authPort = new AuthUseCase(userPersistencePort, jwtAuthPort);

        Mono<AuthResponse> response = authPort.auth(existsUser);

        StepVerifier.create(response)
                .consumeNextWith(authResponse -> {
                    assertEquals(exampleAccessToken, authResponse.getAccessToken());
                    assertEquals(exampleRefreshToken, authResponse.getRefreshToken());
                })
                .expectNextCount(0)
                .verifyComplete();
    }
}
