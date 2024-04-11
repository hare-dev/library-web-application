package com.haredev.library.user.infrastructure.mail;

import com.haredev.library.user.domain.VerificationMailSenderClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
final class VerificationMailSender implements VerificationMailSenderClient {
    private final VerificationMailCreator verificationMailCreator;
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Async
    public void send(final String name,
                     final String receiver,
                     final String token) {
        SimpleMailMessage message = verificationMailCreator.createVerificationMail(name, receiver, token);
        mailSender.send(message);
    }
}
