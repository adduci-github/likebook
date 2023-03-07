package com.likebook.auth.application.port.out;

public interface JWTAuthPort {
    String generateToken(String username);
}
