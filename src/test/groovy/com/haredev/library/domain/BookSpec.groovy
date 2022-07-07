package com.haredev.library.domain

import com.haredev.library.book.domain.BookConfiguration
import com.haredev.library.book.domain.BookFacade
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BookSpec extends Specification {
    BookFacade facade = new BookConfiguration().bookFacade()

    def "Add book to inventory"() {
        given:
        facade.addBook()
    }

}
