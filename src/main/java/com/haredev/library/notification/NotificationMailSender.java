package com.haredev.library.notification;

public interface NotificationMailSender {
    void sendRegistrationVerificationMail(final String name,
                                          final String receiver,
                                          final String token);
}
