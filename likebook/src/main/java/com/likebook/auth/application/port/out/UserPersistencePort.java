package com.likebook.auth.application.port.out;

import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.domain.Auth;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<Auth> findByUsername(AuthRequest request);
}
