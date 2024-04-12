package com.haredev.library.user.domain;

import org.springframework.mail.SimpleMailMessage;

public interface VerificationMailSenderClient {
    void send(final SimpleMailMessage verificationMailMessage);
}
