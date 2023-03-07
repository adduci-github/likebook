package com.likebook.user.adapter.out.jwt;

import com.likebook.config.jwt.JWTUtil;
import com.likebook.config.security.UserDetails;
import com.likebook.user.application.port.out.JWTAuthPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JWTAuthAdapter implements JWTAuthPort {
    private final JWTUtil jwtUtil;

    @Override
    public String generateToken(String username) {
        return jwtUtil.generateToken(
                UserDetails.builder()
                        .username(username)
                        .build()
        );
    }
}
