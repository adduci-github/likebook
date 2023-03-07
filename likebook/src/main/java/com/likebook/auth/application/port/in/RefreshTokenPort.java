package com.likebook.auth.application.port.in;

import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.application.port.in.dto.RefreshTokenResponse;
import reactor.core.publisher.Mono;

public interface RefreshTokenPort {
    Mono<RefreshTokenResponse> refreshToken(RefreshTokenRequest request);
}
