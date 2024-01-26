package com.haredev.library.security;

import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserLoginDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CustomUserDetailsService implements UserDetailsService {
    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Either<UserError, UserLoginDto> userDto = userFacade.findByUsername(username);
        final String login = userDto.get().getUsername();
        final String password = userDto.get().getPassword();
        final List<CustomGrantedAuthority> authorities = userDto.get().getAuthorities()
                .stream().map(authority -> new CustomGrantedAuthority(authority.name())).collect(Collectors.toList());
        return new User(login, password, authorities);
    }
}
