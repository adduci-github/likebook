package com.likebook.auth.adapter.out.persistence.entity;

import com.likebook.config.security.Role;
import com.likebook.config.security.UserDetails;
import com.likebook.auth.domain.Auth;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "users")
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @Column("user_name")
    private String username;
    @Column("password")
    private String password;

    public Auth toUser() {
        return Auth.builder()
                .username(this.username)
                .build();
    }
}
