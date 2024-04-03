package com.haredev.library.user

import com.haredev.library.notification.NotificationFacade
import com.haredev.library.notification.NotificationTestConfiguration
import com.haredev.library.user.domain.InMemoryUserRepository
import com.haredev.library.user.domain.InMemoryVerificationTokenRepository
import com.haredev.library.user.domain.UserConfiguration
import com.haredev.library.user.domain.UserFacade

class UserApplicationTestConfiguration {
    static final UserFacade getConfiguration() {
        final NotificationFacade notificationFacade = NotificationTestConfiguration.getConfiguration()
        return new UserConfiguration().userFacade(new InMemoryUserRepository(), new InMemoryVerificationTokenRepository(), notificationFacade)
    }
}
