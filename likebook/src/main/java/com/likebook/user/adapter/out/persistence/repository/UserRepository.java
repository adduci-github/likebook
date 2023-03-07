package com.likebook.user.adapter.out.persistence.repository;

import com.likebook.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {
    Mono<UserEntity> findById(String username);
}
