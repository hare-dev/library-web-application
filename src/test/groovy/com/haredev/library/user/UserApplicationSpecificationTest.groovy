package com.haredev.library.user

import com.haredev.library.user.domain.InMemoryUserRepository
import com.haredev.library.user.domain.UserConfiguration
import com.haredev.library.user.domain.api.Authority
import com.haredev.library.user.domain.api.UserError
import com.haredev.library.user.samples.SampleUsers
import spock.lang.Specification

import static com.haredev.library.user.domain.api.UserError.DUPLICATED_USERNAME
import static com.haredev.library.user.domain.api.UserError.USER_NOT_FOUND
import static com.haredev.library.user.samples.SampleUsers.createUserSample

class UserApplicationSpecificationTest extends Specification {
    def facade = new UserConfiguration().userFacade(new InMemoryUserRepository())

    static final int page = 20;
    def final USER = createUserSample(0L, "user", "a12345678Z!@")
    def final ADMIN = createUserSample(1L, "admin", "a12345678Z!@")

    def "Should be empty"() {
        expect:
        facade.fetchAllUsers(20).isEmpty()
    }

    def "Should register one user"() {
        given: "Add user to system"
        facade.registerAsUser(USER)

        when: "Find user by username"
        def EXPECTED_USERNAME = facade.findByUsername(USER.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == USER.username()
    }

    def "Should register one admin"() {
        given: "Add user as admin to system"
        facade.registerAsAdmin(ADMIN)

        when: "Find user by username"
        def EXPECTED_USERNAME = facade.findByUsername(ADMIN.username()).get().username()

        then: "Compare registered user with founded user"
        EXPECTED_USERNAME == ADMIN.username()
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
        UserError EXPECTED_ERROR = facade.registerAsUser(USER).getLeft()

        then: "Return error because username is duplicated"
        EXPECTED_ERROR == DUPLICATED_USERNAME
    }

    def "Should not register admin because username is duplicated"() {
        when: "Add admin with the same username to system"
        facade.registerAsAdmin(ADMIN)
        UserError EXPECTED_ERROR = facade.registerAsAdmin(ADMIN).getLeft()

        then: "Return error because username is duplicated"
        EXPECTED_ERROR == DUPLICATED_USERNAME
    }

    def "Should find user by id"() {
        given: "Add user to system"
        facade.registerAsUser(USER)

        when: "Find user by userId"
        def EXPECTED_USERNAME = facade.findUserById(USER.userId()).get().username()

        then: "Compare added user with founded user"
        EXPECTED_USERNAME == USER.username()
    }

    def "Should not find user by id and return error"() {
        when: "Try to find user with not exist id"
        def EXPECTED_ERROR = facade.findUserById(100).getLeft()

        then: "Return error with not found user"
        EXPECTED_ERROR == USER_NOT_FOUND
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

    def "Should promote user to admin"() {
        given: "Add one user"
        facade.registerAsUser(USER)

        when: "Promote user to be admin"
        facade.promoteToAdmin(USER.userId())
        def RESULT = facade.findUserById(USER.userId())

        then: "User has two authorities"
        RESULT.get().authorities() == Set.of(Authority.ADMIN, Authority.USER)
    }

    def "Should not promote not exist user to admin"() {
        when: "Try to promote not exist user"
        def RESULT = facade.promoteToAdmin(SampleUsers.notExistUserWithThisId).getLeft()

        then: "Return user not found"
        RESULT == USER_NOT_FOUND
    }
}
