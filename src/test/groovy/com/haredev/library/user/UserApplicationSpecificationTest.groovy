package com.haredev.library.user

import com.haredev.library.TimeManager
import com.haredev.library.user.domain.InMemoryUserRepository
import com.haredev.library.user.domain.InMemoryVerificationTokenRepository
import com.haredev.library.user.domain.UserConfiguration
import com.haredev.library.user.domain.api.Authority
import com.haredev.library.user.domain.api.error.UserError
import pl.amazingcode.timeflow.TestTime
import spock.lang.Specification

import java.time.Duration
import java.time.temporal.ChronoUnit

import static com.haredev.library.user.domain.api.error.UserError.*
import static com.haredev.library.user.samples.SampleUsers.*

class UserApplicationSpecificationTest extends Specification {
    def facade = new UserConfiguration().userFacade(new InMemoryUserRepository(), new InMemoryVerificationTokenRepository())

    def setup() {
        TimeManager.setClock()
    }

    def cleanup() {
        TimeManager.resetClock()
    }

    def "Should be empty"() {
        expect:
        facade.fetchAllUsers(page).isEmpty()
    }

    def "Should register one user"() {
        given: "Add user to system"
        def USERNAME = facade.registerAsUser(USER).get().username()

        when: "Find user by username"
        def EXPECTED_USERNAME = facade.findByUsername(USER.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == USERNAME
    }

    def "Should register one admin"() {
        given: "Add user as admin to system"
        def USERNAME = facade.registerAsAdmin(ADMIN).get().username()

        when: "Find user by username"
        def EXPECTED_USERNAME = facade.findByUsername(ADMIN.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == USERNAME
    }

    def "Should register user and admin"() {
        given: "Add user and admin to system"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Find user and admin by username"
        def EXPECTED_USERNAME_USER = facade.findByUsername(USER.username()).get().username()
        def EXPECTED_USERNAME_ADMIN = facade.findByUsername(ADMIN.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME_USER == USER.username()
        EXPECTED_USERNAME_ADMIN == ADMIN.username()
    }

    def "Should not register user because username is duplicated"() {
        when: "Add users with the same username to system"
        facade.registerAsUser(USER)
        def RESPONSE_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Return error because username is duplicated"
        RESPONSE_ERROR == DUPLICATED_USERNAME
    }

    def "Should not register admin because username is duplicated"() {
        when: "Add admin with the same username to system"
        facade.registerAsAdmin(ADMIN)
        UserError RESPONSE_ERROR = facade.registerAsAdmin(ADMIN).getLeft()

        then: "Return error because username is duplicated"
        RESPONSE_ERROR == DUPLICATED_USERNAME
    }

    def "Should find user by id"() {
        given: "Add user to system"
        def USER = facade.registerAsUser(USER).get()

        when: "Find user by id"
        def EXPECTED_USERNAME = facade.findUserById(USER.id()).get().username()

        then: "Compare added user with founded user"
        EXPECTED_USERNAME == USER.username()
    }

    def "Should not find user by id and return error"() {
        when: "Try to find user with not exist id"
        def ERROR_RESPONSE = facade.findUserById(notExistUserWithThisId).getLeft()

        then: "Return error with not found user"
        ERROR_RESPONSE == USER_NOT_FOUND
    }

    def "Should return list with users"() {
        given: "Add two users"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Return list with users"
        def EXPECTED_SIZE = facade.fetchAllUsers(page).size()

        then: "Compare expected size with list size"
        EXPECTED_SIZE == 2
    }

    def "Should remove one user"() {
        given: "Add one user"
        facade.registerAsUser(USER)

        when: "Remove one user"
        facade.removeUserById(USER.userId())

        then: "No user in system"
        facade.fetchAllUsers(page).isEmpty()
    }

    def "Should remove user and admin"() {
        given: "Add one user and one admin"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Remove user and admin"
        facade.removeUserById(USER.userId())
        facade.removeUserById(ADMIN.userId())

        then: "No user in system"
        facade.fetchAllUsers(page).isEmpty()
    }

    def "Should promote user to be admin"() {
        given: "Add one user"
        facade.registerAsUser(USER)

        when: "Promote user to be admin"
        def EXPECTED_AUTHORITIES = facade.promoteToAdmin(USER.userId()).get().authorities()

        then: "User has two authorities"
        EXPECTED_AUTHORITIES == Set.of(Authority.ADMIN, Authority.USER)
    }

    def "Should not promote user to be admin with admin authority"() {
        given: "Add one user"
        facade.registerAsAdmin(ADMIN)

        when: "Promote user with admin authority to be admin"
        def RESPONSE_ERROR = facade.promoteToAdmin(ADMIN.userId()).getLeft()

        then: "User is already admin response error"
        RESPONSE_ERROR == USER_IS_ALREADY_ADMIN
    }

    def "Should not promote for not exist user to be admin"() {
        when: "Try to promote not exist user"
        def RESPONSE_ERROR = facade.promoteToAdmin(notExistUserWithThisId).getLeft()

        then: "Return user not found"
        RESPONSE_ERROR == USER_NOT_FOUND
    }

    def "Should change username for user or admin"() {
        given: "Add one user"
        facade.registerAsUser(USER)
        final String NEW_USERNAME = "user_updated"

        when: "Change username for user"
        def EXPECTED_USERNAME = facade.changeUsername(USER.userId(), NEW_USERNAME).get().username()

        then: "Compare old username with new username"
        EXPECTED_USERNAME == NEW_USERNAME
    }

    def "Should not change username for not exist user or admin"() {
        when: "Try change username for not exists user or admin"
        final String newUserUsername = "user_updated"
        def RESPONSE_ERROR = facade.changeUsername(notExistUserWithThisId, newUserUsername).getLeft()

        then: "Return user not found"
        RESPONSE_ERROR == USER_NOT_FOUND
    }

    def "Should create verification token for user and confirm registration"() {
        given: "Register user"
        def USER_ID = facade.registerAsUser(USER).get().id()

        when: "Create verification token for user and confirm registration"
        def TOKEN = facade.createConfirmationToken(USER_ID).get().token()
        def EXPECTED_ACTIVATION_STATUS = facade.confirmRegistration(TOKEN, USER_ID).get().isActivated()
        def USER_ACTIVATION_STATUS = facade.findUserById(USER_ID).get().isActivated()

        then: "User is activated"
        EXPECTED_ACTIVATION_STATUS == USER_ACTIVATION_STATUS
    }

    def "Should not build verification token for not exist user"() {
        when: "Try to build verification token for not exist user"
        def RESPONSE_ERROR = facade.createConfirmationToken(notExistUserWithThisId).getLeft()

        then: "Return user not found"
        RESPONSE_ERROR == USER_NOT_FOUND
    }

    def "Should not confirm registration with not exist token"() {
        given: "Register user"
        def USER_ID = facade.registerAsUser(USER).get().id()

        when: "Create verification token and try to confirm registration with not exist token"
        def RESPONSE_ERROR = facade.confirmRegistration(notExistToken, USER_ID).getLeft()

        then: "Return verification token not found"
        RESPONSE_ERROR == VERIFICATION_TOKEN_NOT_FOUND
    }

    def "Should not confirm registration for user which is already activated"() {
        given: "Register user"
        def USER_ID = facade.registerAsUser(USER).get().id()

        when: "Create verification token which is already confirmed"
        def TOKEN = facade.createConfirmationToken(USER_ID).get().token()
        facade.confirmRegistration(TOKEN, USER_ID)
        def RESPONSE_ERROR = facade.confirmRegistration(TOKEN, USER_ID).getLeft()

        then: "Return verification token not found"
        RESPONSE_ERROR == VERIFICATION_TOKEN_IS_ALREADY_CONFIRMED
    }

    def "Should not confirm user registration with expired verification token"() {
        given: "Register user"
        def USER_ID = facade.registerAsUser(USER).get().id()

        when: "Create verification token and try to confirm registration with expired token"
        def TOKEN = facade.createConfirmationToken(USER_ID).get().token()
        jumpInDaysForward(3)
        def RESPONSE_ERROR = facade.confirmRegistration(TOKEN, USER_ID).getLeft()

        then: "Return verification token is expired"
        RESPONSE_ERROR == VERIFICATION_TOKEN_IS_EXPIRED
    }

    static final int page = 20
    def final USER = createUserSample(0L, "user", "a12345678Z!@", "user_example@gmail.com")
    def final ADMIN = createUserSample(1L, "admin", "a12345678Z!@", "admin_example@gmail.com")

    private static final void jumpInDaysForward(final Integer days) {
        final Duration duration = Duration.of(days, ChronoUnit.DAYS)
        TestTime.testInstance().fastForward(duration)
    }
}
