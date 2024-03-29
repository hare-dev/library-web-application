package com.haredev.library.user.domain;

import com.haredev.library.infrastructure.entity.BaseEntity;
import com.haredev.library.infrastructure.mapper.AuthoritiesToStringConverter;
import com.haredev.library.user.domain.api.AccountStatus;
import com.haredev.library.user.domain.api.Authority;
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
@Table(name = "users")
class UserApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Convert(converter = AuthoritiesToStringConverter.class)
    private Set<Authority> authorities;

    void changeUsername(String username) {
        this.username = username;
    }

    void activateAccount() {
        this.accountStatus = AccountStatus.ACTIVATED;
    }

    void promoteToAdmin() {
        this.authorities.add(Authority.ADMIN);
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
