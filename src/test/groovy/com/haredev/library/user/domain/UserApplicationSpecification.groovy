package com.haredev.library.user.domain

import com.haredev.library.user.controller.input.RegistrationRequest
import com.haredev.library.user.domain.api.UserError
import com.haredev.library.user.samples.SampleUsers
import spock.lang.Specification

import static com.haredev.library.user.domain.api.UserError.*

class UserApplicationSpecification extends Specification {
    def facade = new UserConfiguration().userFacade(new InMemoryUserRepository())

    static final int page = 20;
    def final USER = SampleUsers.createUserSample(0L,"user", "password")
    def final ADMIN = SampleUsers.createUserSample(1L,"admin", "password")

    def "Should be empty"() {
        expect:
        facade.fetchAllUsers(20).isEmpty()
    }

    def "Should register one user" () {
        given: "Add user to system"
        facade.registerAsUser(USER)

        when: "Find user by username"
        String EXPECTED_USERNAME = facade.findByUsername(USER.username).get().username

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == USER.username
    }

    def "Should register one admin" () {
        given: "Add user as admin to system"
        facade.registerAsAdmin(ADMIN)

        when: "Find user by username"
        String EXPECTED_USERNAME = facade.findByUsername(ADMIN.username).get().username

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == ADMIN.username
    }

    def "Should register user and admin" () {
        given: "Add user and admin to system"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Find user and admin by username"
        String EXPECTED_USERNAME_USER = facade.findByUsername(USER.username).get().username
        String EXPECTED_USERNAME_ADMIN = facade.findByUsername(ADMIN.username).get().username

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME_USER == USER.username
        EXPECTED_USERNAME_ADMIN == ADMIN.username
    }

    def "Should not register user because username is duplicated" () {
        when: "Add users with the same username to system"
        facade.registerAsUser(USER)
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Return error because username is duplicated"
        EXPECTED_ERROR == USERNAME_DUPLICATED
    }

    def "Should not register user with null username" () {
        given: "Create user with null username"
        RegistrationRequest USER = RegistrationRequest.builder()
                .userId(1)
                .password("password")
                .username(SampleUsers.nullUsername())
                .build()

        when: "Return null username error"
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Compare expected error with result error"
        EXPECTED_ERROR == USERNAME_IS_NULL
    }

    def "Should not register user with empty username" () {
        given: "Create user with empty username"
        RegistrationRequest USER = RegistrationRequest.builder()
                .userId(1)
                .password("password")
                .username(SampleUsers.emptyUsername())
                .build()

        when: "Return empty username error"
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Compare expected error with result error"
        EXPECTED_ERROR == USERNAME_IS_EMPTY
    }

    def "Should not register user with null password" () {
        given: "Create user with null password"
        RegistrationRequest USER = RegistrationRequest.builder()
                .userId(1)
                .password(SampleUsers.nullPassword())
                .username("user")
                .build()

        when: "Return null password error"
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Compare expected error with result error"
        EXPECTED_ERROR == PASSWORD_IS_NULL
    }

    def "Should not register user with empty password" () {
        given: "Create user with empty password"
        RegistrationRequest USER = RegistrationRequest.builder()
                .userId(1)
                .password(SampleUsers.emptyPassword())
                .username("user")
                .build()

        when: "Return null password error"
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Compare expected error with result error"
        EXPECTED_ERROR == PASSWORD_IS_EMPTY
    }

    def "Should find user by id" () {
        given: "Add user to system"
        facade.registerAsUser(USER)

        when: "Find user by userId"
        String EXPECTED_USERNAME = facade.findUserById(USER.userId).get().username

        then: "Compare added user with founded user"
        EXPECTED_USERNAME == USER.username
    }

    def "Should not find user by id and return error" () {
        when: "Try to find user with not exist id"
        UserError EXPECTED_ERROR = facade.findUserById(100).getLeft()

        then: "Return error with not found user"
        EXPECTED_ERROR == USER_NOT_FOUND
    }

    def "Should return list with users" () {
        given: "Add two users"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Return list with users"
        int EXPECTED_SIZE = facade.fetchAllUsers(page).size()

        then: "Compare expected size with list size"
        EXPECTED_SIZE == 2
    }

    def "Should remove one user" () {
        given: "Add one user"
        facade.registerAsUser(USER)

        when: "Remove one user"
        facade.removeUserById(USER.userId)

        then: "No user in system"
        facade.fetchAllUsers(page).isEmpty()
    }

    def "Should remove user and admin" () {
        given: "Add one user and one admin"
        facade.registerAsUser(USER)
        facade.registerAsAdmin(ADMIN)

        when: "Remove user and admin"
        facade.removeUserById(USER.userId)
        facade.removeUserById(ADMIN.userId)

        then: "No user in system"
        facade.fetchAllUsers(page).isEmpty()
    }
}
