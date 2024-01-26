package com.haredev.library.user.domain;

import com.haredev.library.infrastructure.entity.BaseEntity;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.Authority;
import com.haredev.library.user.domain.dto.UserLoginDto;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.mapper.AuthoritiesToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
class UserApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_generator")
    private Long userId;
    private String username;
    private String password;

    @Convert(converter = AuthoritiesToStringConverter.class)
    private Set<Authority> authorities;

    public static UserApplication newInstance(Long userId, String username, String password, Authority... authorities) {
        final UserApplication userApplication = new UserApplication();
        userApplication.userId = userId;
        userApplication.username = username;
        userApplication.password = password;
        userApplication.authorities = Set.of(authorities);
        return userApplication;
    }

    RegistrationResponse toRegistrationResponse() {
        return RegistrationResponse.builder()
                .userId(userId)
                .username(username)
                .build();
    }

    UserDetailsDto toUserDetails() {
        return UserDetailsDto.builder()
                .userId(userId)
                .username(username)
                .authorities(authorities)
                .build();
    }

    UserLoginDto toLoginDto() {
        return UserLoginDto.builder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApplication userApplication)) return false;
        if (!super.equals(o)) return false;
        if (!Objects.equals(userId, userApplication.userId)) return false;
        return Objects.equals(username, userApplication.username);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
