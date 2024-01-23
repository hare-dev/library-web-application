package com.haredev.library.infrastructure.errors;

import io.vavr.control.Either;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor
public final class ResponseResolver {
    public static ResponseEntity<?> resolve(Either<? extends ResponseError, ?> input) {
        return input
                .map(ResponseEntity::ok)
                .getOrElseGet(ResponseResolver::createErrorMessage);
    }

    private static ResponseEntity createErrorMessage(ResponseError error) {
        ErrorMessage message = new ErrorMessage(error.getMessage());
        int httpCode = error.getHttpCode();
        return new ResponseEntity<>(message, HttpStatus.valueOf(httpCode));
    }

    public static <T> ResponseEntity<List<T>> resolve(List<T> list) {
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
