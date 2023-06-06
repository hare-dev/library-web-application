package com.haredev.library.user.domain;

import com.haredev.library.infrastructure.entity.BaseEntity;
import com.haredev.library.user.controller.validation.UserRegistrationResponse;
import com.haredev.library.user.domain.api.Authority;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserDto;
import com.haredev.library.user.domain.mapper.AuthoritiesToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_generator")
    private Long userId;
    private String username;
    private String password;

    @Convert(converter = AuthoritiesToStringConverter.class)
    private Set<Authority> authorities;

    public static User newInstance(String username, String password, Authority... authorities) {
        final User user = new User();
        user.username = username;
        user.password = password;
        user.authorities = Set.of(authorities);
        return user;
    }

    UserRegistrationResponse registrationResponse() {
        return UserRegistrationResponse.builder()
                .userId(userId)
                .username(username)
                .build();
    }

    UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
                .username(username)
                .authorities(authorities)
                .build();
    }

    UserDetailsDto userDetails() {
        return UserDetailsDto.builder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;
        if (!Objects.equals(userId, user.userId)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
