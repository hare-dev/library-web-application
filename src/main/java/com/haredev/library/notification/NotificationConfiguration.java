package com.haredev.library.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class NotificationConfiguration {
    @Bean
    RegistrationVerificationMailSender registrationVerificationMailSender(@Value("${spring.mail.username}") final String sender,
                                                                          final Environment environment,
                                                                          final JavaMailSender javaMailSender) {
        final RegistrationVerificationMailCreator registrationVerificationMailCreator =
                new RegistrationVerificationMailCreator(environment, sender);
        return new RegistrationVerificationMailSender(registrationVerificationMailCreator, javaMailSender);
    }
}
