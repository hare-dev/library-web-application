package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;
import org.springframework.lang.NonNull;

public final class Author {
    private static final int VALID_AUTHOR_LENGTH = 50;
    private Author() { }

    public static Validation<String, String> validate(@NonNull String author) {
        return isValid(author)
                ? Validation.valid(author)
                : Validation.invalid("Author length cannot be longer than 50");
    }

    private static boolean isValid(String author) { return author.length() < VALID_AUTHOR_LENGTH; }
}
