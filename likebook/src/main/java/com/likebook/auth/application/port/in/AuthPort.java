package com.likebook.auth.application.port.in;

import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.application.port.in.dto.AuthResponse;
import reactor.core.publisher.Mono;

public interface AuthPort {
    Mono<AuthResponse> auth(AuthRequest request);
}
