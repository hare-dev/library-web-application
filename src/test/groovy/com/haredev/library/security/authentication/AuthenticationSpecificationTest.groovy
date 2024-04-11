package com.haredev.library.security.authentication

import com.haredev.library.security.authentication.samples.AuthenticationCredentialsSample
import com.haredev.library.user.UserApplicationTestConfiguration
import com.haredev.library.user.domain.VerificationMailSenderClient
import spock.lang.Specification

import static com.haredev.library.security.authentication.errors.AuthenticationError.WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD

class AuthenticationSpecificationTest extends Specification implements AuthenticationCredentialsSample {
    final def verificationMailSenderClient = Mock(VerificationMailSenderClient)
    final def userFacade = UserApplicationTestConfiguration.getConfiguration(verificationMailSenderClient)
    final def authenticationFacade = AuthenticationTestConfiguration.getConfiguration(verificationMailSenderClient)

    def "Should sign in user"() {
        when: "Register user"
        userFacade.registerAsUser(user)

        then: "User log in to system"
        authenticationFacade.signIn(authentication_correct_credentials)
    }

    def "Should not sign in user with incorrect password"() {
        given: "Register correct user"
        userFacade.registerAsUser(user)

        when: "Login user with incorrect password"
        def error = authenticationFacade.signIn(authentication_with_incorrect_password).getLeft()

        then: "Return wrong username or password"
        error == WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD
    }

    def "Should not sign in user with incorrect username"() {
        given: "Register user"
        userFacade.registerAsUser(user)

        when: "Login user with incorrect username"
        def error = authenticationFacade.signIn(authentication_with_incorrect_username).getLeft()

        then: "Return wrong username or password"
        error == WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD
    }
}
