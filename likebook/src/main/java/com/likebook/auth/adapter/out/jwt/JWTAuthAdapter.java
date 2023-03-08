package com.likebook.auth.adapter.out.jwt;

import com.likebook.auth.adapter.out.persistence.entity.RefreshTokenEntity;
import com.likebook.auth.adapter.out.persistence.repository.RefreshTokenRepository;
import com.likebook.auth.application.port.in.dto.RefreshTokenRequest;
import com.likebook.auth.domain.Token;
import com.likebook.config.jwt.JWTUtil;
import com.likebook.config.security.Role;
import com.likebook.config.security.UserDetails;
import com.likebook.auth.application.port.out.JWTAuthPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class JWTAuthAdapter implements JWTAuthPort {
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Mono<Token> generateToken(String username) {
        Token token = jwtUtil.issueToken(
                UserDetails.builder()
                        .username(username)
                        .build()
        );

        return refreshTokenRepository.save(new RefreshTokenEntity(token.getRefreshToken(), username))
                .filter(isSave -> isSave)
                .map(isSave -> token);
    }

    @Override
    public Mono<Token> refreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(jwtUtil::reIssueToken);
    }
}
