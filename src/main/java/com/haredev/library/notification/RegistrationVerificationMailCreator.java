package com.haredev.library.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

@RequiredArgsConstructor
class RegistrationVerificationMailCreator {
    private final Environment environment;
    @Value("${spring.mail.username}")
    private final String sender;

    public SimpleMailMessage createVerificationMail(final String name,
                                                    final String receiver,
                                                    final String token) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setText(buildMessage(name, token));
            message.setFrom(sender);
            message.setTo(receiver);
            message.setSubject("Account Registration Verification");
            return message;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }

    private String buildMessage(final String name, final String token) {
        final String port = environment.getProperty("server.port");
        final String applicationHost = "http://localhost:" + port;
        return "Hello " + name +
                ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(applicationHost, token) +
                "\n\nThe support Team";
    }

    private String getVerificationUrl(final String applicationHost, final String token) {
        return applicationHost + "/users/registration/confirmation?token=" + token;
    }
}
