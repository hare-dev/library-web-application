package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;

public final class Language {
    private static final int VALID_LANGUAGE_LENGTH = 30;
    private Language() { }

    public static Validation<String, String> validate(final String language) {
        return isValid(language)
                ? Validation.valid(language)
                : Validation.invalid("Language length cannot be longer than 30");
    }

    private static boolean isValid(String language) { return language.length() < VALID_LANGUAGE_LENGTH; }
}
