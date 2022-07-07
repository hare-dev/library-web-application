package com.haredev.library.book.controller.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.Value;

import static io.vavr.API.*;
import static io.vavr.Predicates.isNull;

@Value
public class ValidationErrorsConsumer {
    Seq<String> messages;

    ValidationErrorsConsumer(@JsonProperty("messages") Seq<String> messages) {
        this.messages = messages;
    }


    public static ValidationErrorsConsumer of(Seq<String> messages) {
        return Match(messages).of(
                Case($(isNull()), () -> new ValidationErrorsConsumer(List.empty())),
                Case($(), () -> new ValidationErrorsConsumer(messages))
        );
    }
}
