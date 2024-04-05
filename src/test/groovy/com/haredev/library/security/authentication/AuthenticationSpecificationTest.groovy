package com.haredev.library.security.authentication

import com.haredev.library.notification.NotificationFacade
import com.haredev.library.security.samples.LoginCredentialsSample
import com.haredev.library.user.UserApplicationTestConfiguration
import spock.lang.Specification

import static com.haredev.library.security.authentication.errors.AuthenticationError.WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD
import static com.haredev.library.user.samples.SampleUsers.createUserSample

final class AuthenticationSpecificationTest extends Specification {
    final def notificationFacade = Mock(NotificationFacade)
    final def userFacade = UserApplicationTestConfiguration.getConfiguration(notificationFacade)
    final def authenticationFacade = AuthenticationTestConfiguration.getConfiguration()

    def "Should sign in user"() {
        when: "Register user"
        userFacade.registerAsUser(USER)

        then: "Login user with correct credentials"
        authenticationFacade.signIn(CORRECT_CREDENTIALS)
    }

    def "Should not sign in user with incorrect password"() {
        given: "Register user"
        userFacade.registerAsUser(USER)

        when: "Login user with incorrect password"
        def ERROR_RESPONSE = authenticationFacade.signIn(INCORRECT_PASSWORD).getLeft()

        then: "Return wrong username or password"
        ERROR_RESPONSE == WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD
    }

    def "Should not sign in user with incorrect username"() {
        given: "Register user"
        userFacade.registerAsUser(USER)

        when: "Login user with incorrect username"
        def ERROR_RESPONSE = authenticationFacade.signIn(INCORRECT_USERNAME).getLeft()

        then: "Return wrong username or password"
        ERROR_RESPONSE == WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD
    }

    def final USER = createUserSample(0L, "user", "a12345678Z!@", "user_example@gmail.com")
    def final CORRECT_CREDENTIALS = LoginCredentialsSample.createCorrectCredentialsSample(USER.username(), USER.password())
    def final INCORRECT_PASSWORD = LoginCredentialsSample.createIncorrectPasswordSample(USER.username())
    def final INCORRECT_USERNAME = LoginCredentialsSample.createIncorrectUsernameSample(USER.password())
}
