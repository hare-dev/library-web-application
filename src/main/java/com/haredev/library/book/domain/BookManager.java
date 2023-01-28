package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.BookCreateDto;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class BookManager {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    public Book save(BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book);
    }

    public Option<Book> findOne(Long bookId) {
        return Option.ofOptional(bookRepository.findById(bookId));
    }

    public void removeOne(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> fetchAll() {
        return bookRepository.findAll();
    }

    public List<Book> fetchAllWithPageable(int page, int pageSize) {
        return new ArrayList<>(bookRepository.findAll(PageRequest.of(page, pageSize)));
    }
}
