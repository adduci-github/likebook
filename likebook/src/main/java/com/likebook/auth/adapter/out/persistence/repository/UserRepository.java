package com.likebook.auth.adapter.out.persistence.repository;

import com.likebook.auth.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {
    Mono<UserEntity> findById(String username);
}
