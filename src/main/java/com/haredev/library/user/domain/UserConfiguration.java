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
        final UserManager userManager = new UserManager(userRepository);
        final UserMapper userMapper = new UserMapper();
        final VerificationTokenFactory verificationTokenFactory = new VerificationTokenFactory();
        final ConfirmationTokenValidation confirmationTokenValidation = new ConfirmationTokenValidation();
        final ConfirmationTokenMapper confirmationTokenMapper = new ConfirmationTokenMapper();
        final UserPromotion userPromotion = new UserPromotion(userManager, userMapper);
        final UserUpdate userUpdate = new UserUpdate(userManager, userMapper);
        final UserRegistration userRegistration = new UserRegistration(userFactory, userManager, userMapper);
        final UserRegistrationConfirmation userRegistrationConfirmation = new UserRegistrationConfirmation(
                userManager,
                userMapper,
                verificationTokenFactory,
                confirmationTokenRepository,
                confirmationTokenValidation,
                confirmationTokenMapper);
        return new UserFacade(userPromotion, userUpdate, userRegistration, userRegistrationConfirmation, userManager, userMapper);
    }
}
