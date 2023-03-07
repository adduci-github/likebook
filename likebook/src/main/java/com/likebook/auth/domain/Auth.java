package com.likebook.auth.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Auth {
    private String username;
    private String password;
}
