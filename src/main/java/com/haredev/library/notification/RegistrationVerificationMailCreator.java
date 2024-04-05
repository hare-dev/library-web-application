package com.haredev.library.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

@RequiredArgsConstructor
class RegistrationVerificationMailCreator {
    @Value("${spring.mail.username}")
    private final String emailSender;
    @Value("${server.port}")
    private final int applicationPort;

    public SimpleMailMessage createVerificationMail(final String name,
                                                    final String receiver,
                                                    final String token) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setText(buildMessage(name, token));
            message.setFrom(emailSender);
            message.setTo(receiver);
            message.setSubject("Account Registration Verification");
            return message;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }

    private String buildMessage(final String name, final String token) {
        final String applicationHost = "http://localhost:" + applicationPort;
        return "Hello " + name +
                ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(applicationHost, token) +
                "\n\nThe support Team";
    }

    private String getVerificationUrl(final String applicationHost, final String token) {
        return applicationHost + "/users/registration/confirmation?token=" + token;
    }
}
