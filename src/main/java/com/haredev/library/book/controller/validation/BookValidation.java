package com.haredev.library.book.controller.validation;

import com.haredev.library.book.controller.validation.patterns.*;
import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

public final class BookValidation {
    public Validation<Seq<String>, BookCreateDto> validate(BookCreateDto request) {
        return Validation.combine(
                        Title.validate(request.getTitle()),
                        Author.validate(request.getAuthor()),
                        Isbn.validate(request.getIsbn()),
                        Publisher.validate(request.getPublisher()),
                        PageNumber.validate(request.getPageNumber()),
                        Language.validate(request.getLanguage()),
                        Description.validate(request.getDescription())
        ).ap((title, author, isbn, publisher, pageNumber, language, description) -> BookCreateDto.builder()
                        .title(title)
                        .author(author)
                        .isbn(isbn)
                        .publisher(publisher)
                        .yearPublication(request.getYearPublication())
                        .pageNumber(pageNumber)
                        .language(language)
                        .bookType(request.getBookType())
                        .bookCover(request.getBookCover())
                        .bookStatus(request.getBookStatus())
                        .description(description)
                        .build());
    }
}
