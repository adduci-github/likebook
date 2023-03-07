package com.likebook.user.application.port.out;

public interface JWTAuthPort {
    String generateToken(String username);
}
