package com.likebook.auth.application;

import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.application.port.in.dto.RefreshTokenResponse;
import com.likebook.auth.application.port.out.JWTAuthPort;
import com.likebook.auth.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenUseCaseTests {
    private final JWTAuthPort jwtAuthPort = Mockito.mock(JWTAuthPort.class);

    private final String validRefreshToken = "abcd1234";
    private final String invalidRefreshToken = "1234avcd";
    private final Random tokenProvider = new Random();

    @BeforeEach
    public void initJWTAuthPort() {
        when(jwtAuthPort.refreshToken(validRefreshToken))
                .thenReturn(Mono.just(Token.builder().accessToken(String.valueOf(tokenProvider.nextInt())).refreshToken(validRefreshToken).build()));

        when(jwtAuthPort.refreshToken(invalidRefreshToken))
                .thenReturn(Mono.empty());
    }

    @Test
    public void re_issue_with_valid_refresh_token() {
        RefreshTokenUseCase refreshTokenUseCase = new RefreshTokenUseCase(jwtAuthPort);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(validRefreshToken);

        Mono<RefreshTokenResponse> response = refreshTokenUseCase.refreshToken(request);

        StepVerifier.create(response)
                .consumeNextWith(refreshTokenResponse -> {
                    assertEquals(validRefreshToken, refreshTokenResponse.getRefreshToken());
                    assertNotNull(refreshTokenResponse.getAccessToken());
                })
                .verifyComplete();
    }

    @Test
    public void fail_re_issue_with_invalid_refresh_token() {
        RefreshTokenUseCase refreshTokenUseCase = new RefreshTokenUseCase(jwtAuthPort);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken(invalidRefreshToken);

        Mono<RefreshTokenResponse> response = refreshTokenUseCase.refreshToken(request);

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
    }
}
