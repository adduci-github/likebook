package com.likebook.auth.domain;

import com.likebook.auth.adapter.out.persistence.entity.RefreshTokenEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Token {
    private String accessToken;
    private String refreshToken;
}
