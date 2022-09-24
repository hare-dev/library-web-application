package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;
import org.springframework.lang.NonNull;

public final class Publisher {
    private static final int VALID_PUBLISHER_LENGTH = 30;
    private Publisher() { }

    public static Validation<String, String> validate(@NonNull String publisher) {
        return isValid(publisher)
                ? Validation.valid(publisher)
                : Validation.invalid("Publisher length cannot be longer than 30");
    }

    private static boolean isValid(String publisher) { return publisher.length() < VALID_PUBLISHER_LENGTH; }
}
