package com.haredev.library.user.infrastructure.mail;

import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

@NoArgsConstructor
final class VerificationMailCreator {
    public SimpleMailMessage createVerificationMail(final String name,
                                                    final String receiver,
                                                    final String token) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setText(buildMessage(name, token));
            message.setFrom("libraryharedev@gmail.com");
            message.setTo(receiver);
            message.setSubject("Account Verification");
            return message;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }

    private String buildMessage(final String name, final String token) {
        final String applicationPort = System.getProperty("server.port");
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
