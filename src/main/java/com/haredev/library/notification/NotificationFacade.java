package com.haredev.library.notification;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
class RegistrationVerificationMailSender implements NotificationMailSender {
    private final RegistrationVerificationMailCreator registrationVerificationMailCreator;
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Override
    @Async
    public void sendRegistrationVerificationMail(final String name,
                                                 final String receiver,
                                                 final String token) {
        SimpleMailMessage message = registrationVerificationMailCreator.createVerificationMail(name, receiver, token);
        mailSender.send(message);
    }
}
