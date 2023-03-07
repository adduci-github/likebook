package com.likebook.auth.application;

import com.likebook.auth.application.port.in.RefreshTokenPort;
import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.application.port.in.dto.RefreshTokenResponse;
import com.likebook.auth.application.port.out.JWTAuthPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RefreshTokenUseCase implements RefreshTokenPort {
    private final JWTAuthPort jwtAuthPort;

    public Mono<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return jwtAuthPort.refreshToken(request)
                .map(token -> RefreshTokenResponse.builder().accessToken(token.getAccessToken()).refreshToken(token.getRefreshToken()).build());
    }
}
