package com.haredev.library.book

import com.haredev.library.book.domain.BookConfiguration
import com.haredev.library.book.domain.BookFacade
import com.haredev.library.book.domain.InMemoryBookRepository
import com.haredev.library.book.domain.InMemoryCommentRepository

final class BookTestConfiguration {
    static final BookFacade getConfiguration() {
        return new BookConfiguration().bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository())
    }
}
