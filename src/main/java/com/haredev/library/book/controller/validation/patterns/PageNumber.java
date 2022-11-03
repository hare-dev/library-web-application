package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;

import static java.util.Objects.isNull;

public final class PageNumber {
    private PageNumber() { };

    public static Validation<String, Integer> validate(Integer pageNumber) {
        return isNull(pageNumber)
                ? Validation.invalid("Page number cannot be null")
                : Validation.valid(pageNumber);
    }
}
