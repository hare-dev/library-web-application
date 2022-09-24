package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;
import org.springframework.lang.NonNull;

public final class Description {
    private static final int VALID_DESCRIPTION_LENGTH = 200;
    private Description() { }

    public static Validation<String, String> validate(@NonNull String description) {
        return isValid(description)
                ? Validation.valid(description)
                : Validation.invalid("Description length cannot be longer than 200");
    }

    private static boolean isValid(String description) { return description.length() < VALID_DESCRIPTION_LENGTH; }
}
