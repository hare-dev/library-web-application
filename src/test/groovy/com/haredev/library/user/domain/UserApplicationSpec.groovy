package com.haredev.library.user.domain

import com.haredev.library.user.samples.SampleUsers
import spock.lang.Specification

class UserApplicationSpec extends Specification {
    def facade = new UserConfiguration().userFacade(new InMemoryUserRepository())

    def final user = SampleUsers.createUserSample(0L, "password", "user")
}
