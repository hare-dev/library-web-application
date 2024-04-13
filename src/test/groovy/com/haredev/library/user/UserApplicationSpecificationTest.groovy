package com.haredev.library.user


import com.haredev.library.configuration.time.TestTimeProvider
import com.haredev.library.user.domain.VerificationMailSenderClient
import com.haredev.library.user.domain.api.AccountStatus
import com.haredev.library.user.domain.api.Authority
import com.haredev.library.user.samples.SampleUsers
import spock.lang.Specification

import static com.haredev.library.user.domain.api.error.UserError.*

class UserApplicationSpecificationTest extends Specification implements SampleUsers, TestTimeProvider {
    final def verificationMailSenderClient = Mock(VerificationMailSenderClient);
    final def userFacade = UserApplicationTestConfiguration.getConfiguration(verificationMailSenderClient)
    final def PAGE = 20

    def setup() {
        setClock()
    }

    def cleanup() {
        resetClock()
    }

    def "Should be empty"() {
        expect:
        userFacade.fetchAllUsers(PAGE).isEmpty()
    }

    def "Should register one user"() {
        given: "Register one user"
        def username = userFacade.registerAsUser(user).get().username()

        when: "Find user by username"
        def expected_username = userFacade.findByUsername(username).get().username()

        then: "Compare registered user with founded user"
        expected_username == username
    }

    def "Should not register user because username is duplicated"() {
        when: "Register users with the same username"
        userFacade.registerAsUser(user)
        def error = userFacade.registerAsUser(user).getLeft()

        then: "Return username is duplicated"
        error == DUPLICATED_USERNAME
    }

    def "Should find user by id"() {
        given: "Register one user"
        def user = userFacade.registerAsUser(user).get()
        def username = user.username()

        when: "Find user by id"
        def expected_username = userFacade.findUserById(user.id()).get().username()

        then: "Compare added user with founded user"
        expected_username == username
    }

    def "Should not find user by id"() {
        when: "Find not exist user by id"
        def error = userFacade.findUserById(user_with_this_id_not_exist).getLeft()

        then: "Return user not found"
        error == USER_NOT_FOUND
    }

    def "Should return list with users"() {
        given: "Register two users"
        userFacade.registerAsUser(user)

        when: "Return users collection"
        def expected_size = userFacade.fetchAllUsers(PAGE).size()

        then: "System has one user"
        expected_size == 1
    }

    def "Should remove one user"() {
        given: "Register one user"
        userFacade.registerAsUser(user)

        when: "Remove one user"
        userFacade.removeUserById(user.id())

        then: "System has no users"
        userFacade.fetchAllUsers(PAGE).isEmpty()
    }

    def "Should promote user to be admin"() {
        given: "Register one user"
        userFacade.registerAsUser(user)

        when: "Promote user to be admin"
        def expected_authorities = userFacade.promoteToAdmin(user.id()).get().authorities()

        then: "User has admin and user authorities"
        expected_authorities == Set.of(Authority.ADMIN, Authority.USER)
    }

    def "Should not promote user to be admin with admin authority"() {
        given: "Register one user"
        def user = userFacade.registerAsUser(user).get()

        when: "Promote user with admin authority to be admin"
        userFacade.promoteToAdmin(user.id())
        def error = userFacade.promoteToAdmin(user.id()).getLeft()

        then: "Return user is already admin"
        error == USER_IS_ALREADY_ADMIN
    }

    def "Should not promote not exist user to be admin"() {
        when: "Promote not exist user to be admin"
        def error = userFacade.promoteToAdmin(user_with_this_id_not_exist).getLeft()

        then: "Return user not found"
        error == USER_NOT_FOUND
    }

    def "Should change username"() {
        given: "Register one user"
        userFacade.registerAsUser(user)
        def new_username = "user_updated"

        when: "Change username for user"
        userFacade.changeUsername(user.id(), new_username)
        def expected_username = userFacade.findUserById(user.id()).get().username()

        then: "Compare username before and after update"
        expected_username == new_username
    }

    def "Should not change username for not exist user"() {
        when: "Change username for not exist user"
        def new_username = "user_updated"
        def error = userFacade.changeUsername(user_with_this_id_not_exist, new_username).getLeft()

        then: "Return user not found"
        error == USER_NOT_FOUND
    }

    def "Should register one user and confirm registration"() {
        given: "Register one user and create verification token"
        def registration_response = userFacade.registerAsUser(user).get()
        def verification_token = registration_response.verificationToken()

        when: "Confirm user registration"
        def expected_activation_status = userFacade.confirmRegistration(verification_token).get().accountStatus()
        def user_activation_status_after_correct_activation = AccountStatus.ACTIVATED

        then: "User account is activated"
        expected_activation_status == user_activation_status_after_correct_activation
    }

    def "Should not confirm registration with not exist token"() {
        when: "Confirm registration with not exist token"
        def error = userFacade.confirmRegistration(verification_token_with_this_id_not_exist).getLeft()

        then: "Return verification token not found"
        error == VERIFICATION_TOKEN_NOT_FOUND
    }

    def "Should not confirm registration for activated user account"() {
        given: "Register one user and create verification token"
        def registration_response = userFacade.registerAsUser(user).get()
        def verification_token = registration_response.verificationToken()

        when: "Confirm user registration"
        userFacade.confirmRegistration(verification_token)
        def error = userFacade.confirmRegistration(verification_token).getLeft()

        then: "Return verification token is already confirmed"
        error == VERIFICATION_TOKEN_IS_ALREADY_CONFIRMED
    }

    def "Should not confirm user registration for expired verification token"() {
        given: "Register one user and create verification token"
        def registration_response = userFacade.registerAsUser(user).get()
        def verification_token = registration_response.verificationToken()

        when: "Confirm registration with expired token"
        jumpInDaysForward(15)
        def error = userFacade.confirmRegistration(verification_token).getLeft()

        then: "Return verification token is expired"
        error == VERIFICATION_TOKEN_IS_EXPIRED
    }
}
