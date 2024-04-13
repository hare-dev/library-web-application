package com.haredev.library.book.integration

import com.haredev.library.configuration.testcontainers.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@IntegrationTest
class BookIntegrationTest extends Specification {
    @Autowired
    MockMvc mockMvc

    def "Should return status 403 for not authorized user"() {
        when:
        def authorization_result = mockMvc
                .perform(get("/books"))
                .andReturn()
                .getResponse()
                .getStatus()

        then:
        authorization_result == 403
    }
}
