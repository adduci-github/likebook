package com.likebook.user.adapter.out.persistence.entity;

import com.likebook.config.security.Role;
import com.likebook.config.security.UserDetails;
import com.likebook.user.domain.User;
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

    public User toUser() {
        return User.builder()
                .username(this.username)
                .build();
    }

    public UserDetails toUserDetails(List<Role> roles) {
        return UserDetails.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }
}
