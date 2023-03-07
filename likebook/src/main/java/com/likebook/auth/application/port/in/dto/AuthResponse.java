package com.likebook.auth.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;

    public static AuthResponse withToken(String token) {
        return new AuthResponse(token);
    }
}
