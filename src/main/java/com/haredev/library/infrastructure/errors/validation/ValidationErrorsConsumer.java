package com.haredev.library.infrastructure.errors.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.Value;

import static io.vavr.API.*;
import static io.vavr.Predicates.isNull;

@Value
public class ValidationErrorsConsumer {
    Seq<String> errors;

    ValidationErrorsConsumer(@JsonProperty("messages") Seq<String> errors) {
        this.errors = errors;
    }

    public static ValidationErrorsConsumer errorsAsJson(Seq<String> errors) {
        return Match(errors).of(
                Case($(isNull()), () -> new ValidationErrorsConsumer(List.empty())),
                Case($(), () -> new ValidationErrorsConsumer(errors))
        );
    }
}
