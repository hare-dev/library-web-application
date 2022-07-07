package com.haredev.library.book.controller;

import com.haredev.library.book.controller.input.BookRequest;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;

@AllArgsConstructor
public class BookValidation {
    private static final int MAX_AUTHOR_LENGTH = 50;
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_LANGUAGE_LENGTH = 30;
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final int MAX_PUBLISHER_LENGTH = 50;

    private final ISBNValidator isbnValidator;

    public Validation<Seq<String>, BookRequest> validate(BookRequest request) {

        return Validation
               .combine(
                        validationTitle(request.getTitle()),
                        validationAuthor(request.getAuthor()),
                        validationIsbn(request.getIsbn()),
                        validationPublisher(request.getPublisher()),
                        validationPageNumber(request.getPageNumber()),
                        validationLanguage(request.getLanguage()),
                        validationDescription(request.getDescription())
        ).ap((title, author, isbn, publisher, pageNumber, language, description) -> BookRequest.builder()
                        .bookId(request.getBookId())
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

    private Validation<String, String> validationDescription(String description) {
        return description.length() > MAX_DESCRIPTION_LENGTH
                ? Validation.invalid("Description length is longer than " + description.length() + " words")
                : Validation.valid(description);
    }

    private Validation<String, String> validationLanguage(String language) {
        return language.length() > MAX_LANGUAGE_LENGTH
                ? Validation.invalid("Language length is longer than " + language.length() + " words")
                : Validation.valid(language);
    }

    private Validation<String, Integer> validationPageNumber(Integer pageNumber) {
        return pageNumber.toString().isEmpty()
                ? Validation.invalid("Page number cannot be empty")
                : Validation.valid(pageNumber);
    }

    private Validation<String, String> validationPublisher(String publisher) {
        return publisher.length() > MAX_PUBLISHER_LENGTH
                ? Validation.invalid("Publisher length is longer than " + publisher.length() + " words")
                : Validation.valid(publisher);
    }

    private Validation<String, String> validationIsbn(String isbn) {
        return isbnValidator.isValid(isbn)
                ? Validation.valid(isbn)
                : Validation.invalid("Isbn code: " + isbn + " is not correct");
    }

    private Validation<String, String> validationAuthor(String author) {
        return author.length() > MAX_AUTHOR_LENGTH
                ? Validation.invalid("Author length is longer than " + author.length() + " words")
                : Validation.valid(author);
    }

    private Validation<String, String> validationTitle(String title) {
        return title.length() > MAX_TITLE_LENGTH
                ? Validation.invalid("Title length is longer than " + title.length() + " words")
                : Validation.valid(title);
    }
}
