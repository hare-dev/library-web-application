package com.haredev.library.user.domain;

public interface VerificationMailSenderClient {
    void send(final String name, final String receiver, final String token);
}
