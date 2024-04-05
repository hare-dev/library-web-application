package com.haredev.library.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class NotificationConfiguration {
    @Value("${spring.mail.username}")
    private final String emailSender;
    @Value("${spring.mail.host}")
    private final String emailHost;
    @Value("${spring.mail.port}")
    private final int emailPort;
    @Value("${spring.mail.password}")
    private final String emailPassword;
    @Value("${server.port}")
    private final int applicationPort;

    @Bean
    NotificationFacade registrationVerificationMailSender() {
        final RegistrationVerificationMailCreator registrationVerificationMailCreator =
                new RegistrationVerificationMailCreator(emailSender, applicationPort);
        return new NotificationFacade(registrationVerificationMailCreator, javaMailSender());
    }

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailHost);
        javaMailSender.setPort(emailPort);

        javaMailSender.setUsername(emailSender);
        javaMailSender.setPassword(emailPassword);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return javaMailSender;
    }
}
