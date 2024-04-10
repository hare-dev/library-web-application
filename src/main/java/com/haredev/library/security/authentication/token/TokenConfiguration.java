package com.haredev.library.security.authentication.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenConfiguration {
    @Bean
    TokenFacade tokenFacade(@Value("${token.secretKey}")
                            final String secretKey,
                            @Value("${token.expirationTime}")
                            final Long expirationTimeInMilliseconds) {
        return new TokenFacade(secretKey, expirationTimeInMilliseconds);
    }
}
