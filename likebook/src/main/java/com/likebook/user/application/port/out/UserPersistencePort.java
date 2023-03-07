package com.likebook.user.application.port.out;

import com.likebook.user.application.port.in.dto.AuthRequest;
import com.likebook.user.domain.User;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> findByUsername(AuthRequest request);
}
