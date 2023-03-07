package com.likebook.user.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String username;
    private String password;
}
