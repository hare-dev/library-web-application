package com.haredev.library.security.authentication

import com.haredev.library.security.token.TokenFacade
import com.haredev.library.user.domain.UserFacade
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import static com.haredev.library.security.samples.TokenPropertiesSample.secretKey

final class AuthenticationTestConfiguration {
    static final AuthenticationFacade getConfiguration(final UserFacade userFacade) {
        final TokenFacade tokenFacade = new TokenFacade(secretKey)
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
        return new AuthenticationFacade(tokenFacade, userFacade, passwordEncoder)
    }
}
