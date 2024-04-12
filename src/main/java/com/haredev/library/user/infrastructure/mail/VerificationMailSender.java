package com.haredev.library.user.infrastructure.mail;

import com.haredev.library.user.domain.VerificationMailSenderClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
final class VerificationMailSender implements VerificationMailSenderClient {
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Async
    public void send(SimpleMailMessage verificationMail) {
        mailSender.send(verificationMail);
    }
}
