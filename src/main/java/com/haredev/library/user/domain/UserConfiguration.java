package com.haredev.library.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {
    UserFacade userFacade() {
        return userFacade(new InMemoryUserRepository(), new InMemoryConfirmationTokenRepository());
    }

    @Bean
    UserFacade userFacade(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final UserFactory userFactory = new UserFactory(passwordEncoder);
        final UserMapper userMapper = new UserMapper();
        final ConfirmationTokenFactory confirmationTokenFactory = new ConfirmationTokenFactory();
        final ConfirmationTokenValidation confirmationTokenValidation = new ConfirmationTokenValidation();
        final ConfirmationTokenMapper confirmationTokenMapper = new ConfirmationTokenMapper();
        final UserManager userManager = new UserManager(
                userRepository,
                userFactory,
                confirmationTokenFactory,
                confirmationTokenRepository,
                confirmationTokenValidation,
                confirmationTokenMapper,
                userMapper);
        final UserPromotion userPromotion = new UserPromotion(userManager, userMapper);
        final UserUpdate userUpdate = new UserUpdate(userManager, userMapper);
        return new UserFacade(userPromotion, userUpdate, userManager, userMapper);
    }
}
