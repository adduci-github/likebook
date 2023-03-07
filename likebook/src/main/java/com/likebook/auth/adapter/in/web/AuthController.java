package com.likebook.auth.adapter.in.web;

import com.likebook.auth.application.port.in.AuthPort;
import com.likebook.auth.application.port.in.RefreshTokenPort;
import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.application.port.in.dto.AuthResponse;
import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.application.port.in.dto.RefreshTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthPort authPort;
    private final RefreshTokenPort refreshTokenPort;

    @PostMapping("/auth")
    public Mono<ResponseEntity<AuthResponse>> auth(@RequestBody AuthRequest request) {
        return authPort.auth(request)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/auth/refresh")
    public Mono<ResponseEntity<RefreshTokenResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        return refreshTokenPort.refreshToken(request)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
