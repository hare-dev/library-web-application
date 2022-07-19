package com.haredev.library.book.controller.validation;

import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;

@RequiredArgsConstructor
public final class BookValidation {
    private static final int MAX_AUTHOR_LENGTH = 50;
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_LANGUAGE_LENGTH = 30;
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final int MAX_PUBLISHER_LENGTH = 50;

    private final ISBNValidator isbnValidator;

    public Validation<Seq<String>, BookCreateDto> validate(BookCreateDto bookCreateDto) {

        return Validation
               .combine(
                        validationTitle(bookCreateDto.getTitle()),
                        validationAuthor(bookCreateDto.getAuthor()),
                        validationIsbn(bookCreateDto.getIsbn()),
                        validationPublisher(bookCreateDto.getPublisher()),
                        validationPageNumber(bookCreateDto.getPageNumber()),
                        validationLanguage(bookCreateDto.getLanguage()),
                        validationDescription(bookCreateDto.getDescription())
        ).ap((title, author, isbn, publisher, pageNumber, language, description) -> BookCreateDto.builder()
                        .title(title)
                        .author(author)
                        .isbn(isbn)
                        .publisher(publisher)
                        .yearPublication(bookCreateDto.getYearPublication())
                        .pageNumber(pageNumber)
                        .language(language)
                        .bookType(bookCreateDto.getBookType())
                        .bookCover(bookCreateDto.getBookCover())
                        .bookStatus(bookCreateDto.getBookStatus())
                        .description(description)
                        .build());
    }

    private Validation<String, String> validationDescription(String description) {
        return isLongerThanMaximum(description, MAX_DESCRIPTION_LENGTH)
                ? Validation.invalid("Description length is longer than " + description.length() + " words")
                : Validation.valid(description);
    }

    private Validation<String, String> validationLanguage(String language) {
        return isLongerThanMaximum(language, MAX_LANGUAGE_LENGTH)
                ? Validation.invalid("Language length is longer than " + language.length() + " words")
                : Validation.valid(language);
    }

    private Validation<String, Integer> validationPageNumber(Integer pageNumber) {
        return isEmpty(pageNumber.toString())
                ? Validation.invalid("Page number cannot be empty")
                : Validation.valid(pageNumber);
    }

    private Validation<String, String> validationPublisher(String publisher) {
        return isLongerThanMaximum(publisher, MAX_PUBLISHER_LENGTH)
                ? Validation.invalid("Publisher length is longer than " + publisher.length() + " words")
                : Validation.valid(publisher);
    }

    private Validation<String, String> validationIsbn(String isbn) {
        return isNotValid(isbn)
                ? Validation.invalid("Isbn code: " + isbn + " is not correct")
                : Validation.valid(isbn);
    }

    private Validation<String, String> validationAuthor(String author) {
        return isLongerThanMaximum(author, MAX_AUTHOR_LENGTH)
                ? Validation.invalid("Author length is longer than " + author.length() + " words")
                : Validation.valid(author);
    }

    private Validation<String, String> validationTitle(String title) {
        return (isLongerThanMaximum(title, MAX_TITLE_LENGTH))
                ? Validation.invalid("Title length is longer than " + title.length() + " words")
                : Validation.valid(title);
    }

    private boolean isNotValid(String isbn) {
        return !isbnValidator.isValid(isbn);
    }

    private boolean isLongerThanMaximum(String value, int maximum) {
        return value.length() > maximum;
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }
}
