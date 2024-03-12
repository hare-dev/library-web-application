package com.haredev.library.security.authentication;

import com.haredev.library.security.token.TokenFacade;
import com.haredev.library.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class AuthenticationConfiguration {

    @Bean
    AuthenticationFacade authenticationFacade(final TokenFacade tokenFacade, final UserFacade userFacade) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new AuthenticationFacade(tokenFacade, userFacade, passwordEncoder);
    }
}
