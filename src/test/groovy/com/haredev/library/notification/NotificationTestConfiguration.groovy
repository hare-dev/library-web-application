package com.haredev.library.notification

final class NotificationTestConfiguration {
    static final NotificationFacade getConfiguration() {
        return new NotificationConfiguration().notificationFacade()
    }
}
