package com.haredev.library.security.authentication;

import com.haredev.library.security.TokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
class AuthenticationConfiguration {

    @Bean
    AuthenticationFacade authenticationFacade(final TokenFacade tokenFacade,
                                              final AuthenticationManager authenticationManager) {
        return new AuthenticationFacade(tokenFacade, authenticationManager);
    }
}
