package com.likebook.auth.application.port.out;

import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.domain.Token;
import reactor.core.publisher.Mono;

public interface JWTAuthPort {
    Mono<Token> generateToken(String username);
    Mono<Token> refreshToken(RefreshTokenRequest request);
}
