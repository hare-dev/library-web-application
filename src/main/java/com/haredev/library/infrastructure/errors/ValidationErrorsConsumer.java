package com.haredev.library.infrastructure.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.Getter;

import static io.vavr.API.*;
import static io.vavr.Predicates.isNull;

@Getter
public final class ValidationErrorsConsumer {
    private final Seq<String> errors;

    ValidationErrorsConsumer(@JsonProperty("errors") Seq<String> errors) {
        this.errors = errors;
    }

    public static ValidationErrorsConsumer errorsAsJson(Seq<String> errors) {
        return Match(errors).of(
                Case($(isNull()), () -> new ValidationErrorsConsumer(List.empty())),
                Case($(), () -> new ValidationErrorsConsumer(errors))
        );
    }
}
