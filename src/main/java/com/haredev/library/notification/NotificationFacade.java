package com.haredev.library.notification;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class NotificationFacade {
    private final RegistrationVerificationMailCreator registrationVerificationMailCreator;
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Async
    public void sendRegistrationVerificationMail(final String name,
                                                 final String receiver,
                                                 final String token) {
        SimpleMailMessage message = registrationVerificationMailCreator.createVerificationMail(name, receiver, token);
        mailSender.send(message);
    }
}
