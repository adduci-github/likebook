package com.likebook.auth.adapter.out.persistence;

import com.likebook.auth.adapter.out.persistence.entity.UserEntity;
import com.likebook.auth.adapter.out.persistence.repository.UserRepository;
import com.likebook.auth.application.port.in.dto.AuthRequest;
import com.likebook.auth.application.port.out.UserPersistencePort;
import com.likebook.auth.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Auth> findByUsername(AuthRequest request) {
        return userRepository.findById(request.getUsername())
                .filter(userEntity -> passwordEncoder.matches(request.getPassword(), userEntity.getPassword()))
                .map(UserEntity::toUser);
    }
}
