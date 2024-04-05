package com.haredev.library.user

import com.haredev.library.TimeManager
import com.haredev.library.notification.NotificationFacade
import com.haredev.library.user.domain.api.AccountStatus
import com.haredev.library.user.domain.api.Authority
import pl.amazingcode.timeflow.TestTime
import spock.lang.Specification

import java.time.Duration
import java.time.temporal.ChronoUnit

import static com.haredev.library.user.domain.api.error.UserError.*
import static com.haredev.library.user.samples.SampleUsers.*

class UserApplicationSpecificationTest extends Specification {
    final def notificationFacade = Mock(NotificationFacade);
    final def userFacade = UserApplicationTestConfiguration.getConfiguration(notificationFacade)

    def setup() {
        TimeManager.setClock()
    }

    def cleanup() {
        TimeManager.resetClock()
    }

    def "Should be empty"() {
        expect:
        userFacade.fetchAllUsers(page).isEmpty()
    }

    def "Should register one user"() {
        given: "Add user to system"
        def USERNAME = userFacade.registerAsUser(USER).get().username()

        when: "Find user by username"
        def EXPECTED_USERNAME = userFacade.findByUsername(USER.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == USERNAME
    }

    def "Should not register user because username is duplicated"() {
        when: "Add users with the same username to system"
        userFacade.registerAsUser(USER)
        def RESPONSE_ERROR = userFacade.registerAsUser(USER).getLeft()

        then: "Return error because username is duplicated"
        RESPONSE_ERROR == DUPLICATED_USERNAME
    }

    def "Should find user by id"() {
        given: "Add user to system"
        def USER = userFacade.registerAsUser(USER).get()

        when: "Find user by id"
        def EXPECTED_USERNAME = userFacade.findUserById(USER.id()).get().username()

        then: "Compare added user with founded user"
        EXPECTED_USERNAME == USER.username()
    }

    def "Should not find user by id"() {
        when: "Try to find user with not exist id"
        def ERROR_RESPONSE = userFacade.findUserById(notExistUserWithThisId).getLeft()

        then: "Return error with not found user"
        ERROR_RESPONSE == USER_NOT_FOUND
    }

    def "Should return list with users"() {
        given: "Add two users"
        userFacade.registerAsUser(USER)

        when: "Return list with users"
        def EXPECTED_SIZE = userFacade.fetchAllUsers(page).size()

        then: "Compare expected size with list size"
        EXPECTED_SIZE == 1
    }

    def "Should remove one user"() {
        given: "Add one user"
        userFacade.registerAsUser(USER)

        when: "Remove one user"
        userFacade.removeUserById(USER.userId())

        then: "No user in system"
        userFacade.fetchAllUsers(page).isEmpty()
    }

    def "Should promote user to be admin"() {
        given: "Add one user"
        userFacade.registerAsUser(USER)

        when: "Promote user to be admin"
        def EXPECTED_AUTHORITIES = userFacade.promoteToAdmin(USER.userId()).get().authorities()

        then: "User has two authorities"
        EXPECTED_AUTHORITIES == Set.of(Authority.ADMIN, Authority.USER)
    }

    def "Should not promote user to be admin with admin authority"() {
        given: "Add one user"
        def userId = userFacade.registerAsUser(USER).get().id()

        when: "Promote user with admin authority to be admin"
        userFacade.promoteToAdmin(userId)
        def RESPONSE_ERROR = userFacade.promoteToAdmin(userId).getLeft()

        then: "User is already admin response error"
        RESPONSE_ERROR == USER_IS_ALREADY_ADMIN
    }

    def "Should not promote for not exist user to be admin"() {
        when: "Try to promote not exist user"
        def RESPONSE_ERROR = userFacade.promoteToAdmin(notExistUserWithThisId).getLeft()

        then: "Return user not found"
        RESPONSE_ERROR == USER_NOT_FOUND
    }

    def "Should change username for user or admin"() {
        given: "Add one user"
        userFacade.registerAsUser(USER)
        final String NEW_USERNAME = "user_updated"

        when: "Change username for user"
        def EXPECTED_USERNAME = userFacade.changeUsername(USER.userId(), NEW_USERNAME).get().username()

        then: "Compare old username with new username"
        EXPECTED_USERNAME == NEW_USERNAME
    }

    def "Should not change username for not exist user or admin"() {
        when: "Try change username for not exists user or admin"
        final String newUserUsername = "user_updated"
        def RESPONSE_ERROR = userFacade.changeUsername(notExistUserWithThisId, newUserUsername).getLeft()

        then: "Return user not found"
        RESPONSE_ERROR == USER_NOT_FOUND
    }

    def "Should register one user and confirm registration"() {
        given: "Register user"
        def REGISTRATION_RESPONSE = userFacade.registerAsUser(USER).get()
        def VERIFICATION_TOKEN = REGISTRATION_RESPONSE.verificationToken()
        def USER_ID = REGISTRATION_RESPONSE.id()

        when: "Create verification token for user and confirm registration"
        def EXPECTED_ACTIVATION_STATUS = userFacade.confirmRegistration(VERIFICATION_TOKEN).get().accountStatus()
        def USER_ACTIVATION_STATUS = AccountStatus.ACTIVATED

        then: "User is activated"
        EXPECTED_ACTIVATION_STATUS == USER_ACTIVATION_STATUS
    }

    def "Should not confirm registration with not exist token"() {
        given: "Register user"
        userFacade.registerAsUser(USER)

        when: "Create verification token and try to confirm registration with not exist token"
        def RESPONSE_ERROR = userFacade.confirmRegistration(notExistToken).getLeft()

        then: "Return verification token not found"
        RESPONSE_ERROR == VERIFICATION_TOKEN_NOT_FOUND
    }

    def "Should not confirm registration for user which is already activated"() {
        given: "Register user"
        def REGISTRATION_RESPONSE = userFacade.registerAsUser(USER).get()
        def VERIFICATION_TOKEN = REGISTRATION_RESPONSE.verificationToken()

        when: "Create verification token which is already confirmed"
        userFacade.confirmRegistration(VERIFICATION_TOKEN)
        def RESPONSE_ERROR = userFacade.confirmRegistration(VERIFICATION_TOKEN).getLeft()

        then: "Return verification token not found"
        RESPONSE_ERROR == VERIFICATION_TOKEN_IS_ALREADY_CONFIRMED
    }

    def "Should not confirm user registration with expired verification token"() {
        given: "Register user"
        def REGISTRATION_RESPONSE = userFacade.registerAsUser(USER).get()
        def VERIFICATION_TOKEN = REGISTRATION_RESPONSE.verificationToken()

        when: "Create verification token and try to confirm registration with expired token"
        jumpInDaysForward(5)
        def RESPONSE_ERROR = userFacade.confirmRegistration(VERIFICATION_TOKEN).getLeft()

        then: "Return verification token is expired"
        RESPONSE_ERROR == VERIFICATION_TOKEN_IS_EXPIRED
    }

    static final int page = 20
    def final USER = createUserSample(0L, "user", "a12345678Z!@", "user_example@gmail.com")

    private static final void jumpInDaysForward(final Integer days) {
        final Duration duration = Duration.of(days, ChronoUnit.DAYS)
        TestTime.testInstance().fastForward(duration)
    }
}
