package com.haredev.library.book.controller.validation.patterns;

import io.vavr.control.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Isbn {
    private static final String ISBN_PATTERN =
            "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})" +
            "[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89]" +
            "[- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
    private Isbn() { };

    public static Validation<String, String> validate(final String isbn) {
        return isValid(isbn)
                ? Validation.valid(isbn)
                : Validation.invalid("Isbn code is not correct");
    }

    private static boolean isValid(String isbn) {
        Pattern pattern = Pattern.compile(ISBN_PATTERN);
        Matcher matcher = pattern.matcher(isbn);
        return matcher.matches();
    }
}
