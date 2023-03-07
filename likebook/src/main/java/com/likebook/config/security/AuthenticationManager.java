package com.likebook.config.security;

import com.likebook.config.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String token = authentication.getCredentials().toString();
        final String username = jwtUtil.getUsernameFromToken(token);

        return Mono.just(jwtUtil.validateToken(token))
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.empty())
                .map(valid -> createAuthenticationToken(username, token));
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(String username, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        List<String> roles = claims.get("role", List.class);

        return new UsernamePasswordAuthenticationToken(username, null, roles.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
