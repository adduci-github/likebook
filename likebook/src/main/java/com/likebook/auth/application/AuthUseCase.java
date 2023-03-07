package com.likebook.auth.application;

import com.likebook.auth.application.port.in.AuthPort;
import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.application.port.in.dto.AuthResponse;
import com.likebook.auth.application.port.out.JWTAuthPort;
import com.likebook.auth.application.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class AuthUseCase implements AuthPort {
    private final UserPersistencePort userPersistencePort;
    private final JWTAuthPort jwtAuthPort;

    @Override
    public Mono<AuthResponse> auth(AuthRequest request) {
        return userPersistencePort.findByUsername(request)
                .map(user -> jwtAuthPort.generateToken(user.getUsername()))
                .map(AuthResponse::withToken);
    }
}
