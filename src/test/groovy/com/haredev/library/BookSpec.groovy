package com.haredev.library

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class BookSpec extends Specification {

    @Autowired
    private ApplicationContext applicationContext;

    def "My first Spock test"() {
        expect:
        applicationContext != null
    }

}
