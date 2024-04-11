package com.haredev.library.security.authentication


import com.haredev.library.security.authentication.token.TokenFacade
import com.haredev.library.user.UserApplicationTestConfiguration
import com.haredev.library.user.domain.UserFacade
import com.haredev.library.user.domain.VerificationMailSenderClient
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import static com.haredev.library.security.token.samples.AuthenticationTokenPropertiesSample.secretKey
import static com.haredev.library.security.token.samples.AuthenticationTokenPropertiesSample.tokenExpiration

final class AuthenticationTestConfiguration {
    static final AuthenticationFacade getConfiguration(final VerificationMailSenderClient verificationMailSenderClient) {
        final UserFacade userFacade = UserApplicationTestConfiguration.getConfiguration(verificationMailSenderClient)
        final TokenFacade tokenFacade = new TokenFacade(secretKey, tokenExpiration)
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
        return new AuthenticationFacade(tokenFacade, userFacade, passwordEncoder)
    }
}
