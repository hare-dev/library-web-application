package com.haredev.library.infrastructure.errors;

public interface ResponseError {
    String getMessage();
    int getHttpCode();
}
