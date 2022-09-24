package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;
import org.springframework.lang.NonNull;

public final class Title {
    private static final int VALID_TITLE_LENGTH = 100;
    private Title() { }

    public static Validation<String, String> validate(@NonNull String title) {
        return isValid(title)
                ? Validation.valid(title)
                : Validation.invalid("Title length cannot be longer than 100");
    }

    private static boolean isValid(String title) {
        return title.length() < VALID_TITLE_LENGTH;
    }
}
