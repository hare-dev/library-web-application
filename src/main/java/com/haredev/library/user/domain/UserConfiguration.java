package com.haredev.library.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {
    UserFacade userFacade(final VerificationMailSenderClient verificationMailSenderClient) {
        final VerificationTokenRepository verificationTokenRepository = new InMemoryVerificationTokenRepository();
        final UserRepository userRepository = new InMemoryUserRepository();
        return userFacade(userRepository, verificationTokenRepository, verificationMailSenderClient);
    }

    @Bean
    UserFacade userFacade(final UserRepository userRepository,
                          final VerificationTokenRepository verificationTokenRepository,
                          final VerificationMailSenderClient verificationMailSenderClient) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final UserFactory userFactory = new UserFactory(passwordEncoder);
        final UserManager userManager = new UserManager(userRepository);
        final UserMapper userMapper = new UserMapper();
        final UserPromoter userPromoter = new UserPromoter(userManager, userMapper);
        final UserUpdater userUpdater = new UserUpdater(userManager, userMapper);
        final VerificationTokenFactory verificationTokenFactory = new VerificationTokenFactory();
        final VerificationTokenValidator verificationTokenValidator = new VerificationTokenValidator();
        final VerificationRegistration verificationRegistration = new VerificationRegistration(
                userMapper,
                userManager,
                verificationTokenFactory,
                verificationTokenValidator,
                verificationTokenRepository);
        final UserRegistration userRegistration = new UserRegistration(
                userMapper,
                userManager,
                userFactory,
                verificationRegistration,
                verificationMailSenderClient);
        return new UserFacade(userMapper, userManager, userUpdater, userPromoter, userRegistration, verificationRegistration);
    }
}
