package com.haredev.library.user

import com.haredev.library.user.domain.*

class UserApplicationTestConfiguration {
    static final UserFacade getConfiguration(final VerificationMailSenderClient verificationMailSenderClient) {
        return new UserConfiguration().userFacade(
                new InMemoryUserRepository(),
                new InMemoryVerificationTokenRepository(), verificationMailSenderClient)
    }
}
