package com.haredev.library.security;

import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CustomUserDetailsService implements UserDetailsService {
    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginDto userDto = userFacade.findByUsername(username)
                .getOrElseThrow(() -> new UsernameNotFoundException("User not found"));
        final String login = userDto.username();
        final String password = userDto.password();
        final Set<CustomGrantedAuthority> authorities = getAuthorities(userDto);
        return new User(login, password, authorities);
    }

    private Set<CustomGrantedAuthority> getAuthorities(UserLoginDto userDto) {
        return userDto.authorities()
                .stream().map(authority -> new CustomGrantedAuthority(authority.name())).collect(Collectors.toSet());
    }
}
