package com.haredev.library.user.domain;

import com.haredev.library.infrastructure.entity.BaseEntity;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.Authority;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserLoginDto;
import com.haredev.library.infrastructure.mapper.AuthoritiesToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean activationStatus;

    @Convert(converter = AuthoritiesToStringConverter.class)
    private Set<Authority> authorities;

    UserApplication changeUsername(String username) {
        return UserApplication.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .activationStatus(activationStatus)
                .authorities(authorities)
                .build();
    }

    public UserApplication activateAccount() {
        return UserApplication.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .activationStatus(true)
                .authorities(authorities)
                .build();
    }

    void promoteToAdmin() {
        this.authorities.add(Authority.ADMIN);
    }

    public static UserApplication newInstance(Long userId, String username, String email, String password, Authority... authorities) {
        final UserApplication userApplication = new UserApplication();
        userApplication.id = userId;
        userApplication.username = username;
        userApplication.email = email;
        userApplication.password = password;
        userApplication.activationStatus = false;
        userApplication.authorities = new HashSet<>(Set.of(authorities));
        return userApplication;
    }

    RegistrationResponse toRegistrationResponse() {
        return RegistrationResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .activationStatus(activationStatus)
                .build();
    }

    UserDetailsDto toUserDetailsDto() {
        return UserDetailsDto.builder()
                .id(id)
                .username(username)
                .activationStatus(activationStatus)
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
        if (!Objects.equals(id, userApplication.id)) return false;
        return Objects.equals(username, userApplication.username);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
