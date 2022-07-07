package com.haredev.library.infrastructure.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.Value;

import static io.vavr.API.*;
import static io.vavr.Predicates.isNull;

@Value
public class ErrorsConsumer {
    Seq<String> messages;

    ErrorsConsumer(@JsonProperty("messages") Seq<String> messages) {
        this.messages = messages;
    }


    public static ErrorsConsumer of(Seq<String> messages) {
        return Match(messages).of(
                Case($(isNull()), () -> new ErrorsConsumer(List.empty())),
                Case($(), () -> new ErrorsConsumer(messages))
        );
    }
}
