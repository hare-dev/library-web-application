package com.haredev.library.book.domain;

import com.haredev.library.book.controller.output.BookResponse;
import com.haredev.library.book.dto.BookCover;
import com.haredev.library.book.dto.NewBookDto;
import com.haredev.library.book.dto.BookStatus;
import com.haredev.library.book.dto.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "books")
@AllArgsConstructor
@Builder
@Getter
class Book {
        @Id
        private final UUID bookId;
        private final String title;
        private final String author;
        private final String isbn;
        private final String publisher;
        private final Integer yearPublication;
        private final Integer pageNumber;
        private final String language;
        private final BookType bookType;
        private final BookCover bookCover;
        private final BookStatus bookStatus;
        private final String description;

        BookResponse toResponse() {
                return BookResponse.builder()
                        .bookId(bookId)
                        .title(title)
                        .author(author)
                        .isbn(isbn)
                        .publisher(publisher)
                        .yearPublication(yearPublication)
                        .pageNumber(pageNumber)
                        .language(language)
                        .bookType(bookType)
                        .bookCover(bookCover)
                        .bookStatus(bookStatus)
                        .description(description)
                        .build();
        }
}
