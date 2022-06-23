package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.BookCover;
import com.haredev.library.book.domain.dto.NewBookDto;
import com.haredev.library.book.domain.dto.BookStatus;
import com.haredev.library.book.domain.dto.BookType;
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
        private String title;
        private String author;
        private String isbn;
        private String publisher;
        private Integer yearPublication;
        private Integer pageNumber;
        private String language;
        private BookType bookType;
        private BookCover bookCover;
        private BookStatus bookStatus;
        private String description;

        NewBookDto toDTO() {
                return NewBookDto.builder()
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
