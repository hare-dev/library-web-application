package com.haredev.library.security.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenConfiguration {

    @Bean
    TokenFacade tokenFacade(@Value("${token.secretKey}")
                            final String secretKey) {
        return new TokenFacade(secretKey);
    }
}
