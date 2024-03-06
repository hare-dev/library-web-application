package com.haredev.library.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {

    UserFacade userFacade() {
        return userFacade(new InMemoryUserRepository(), new InMemoryVerificationTokenRepository());
    }

    @Bean
    UserFacade userFacade(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final UserFactory userFactory = new UserFactory(passwordEncoder);
        final UserManager userManager = new UserManager(userRepository);
        final UserMapper userMapper = new UserMapper();
        final VerificationTokenFactory verificationTokenFactory = new VerificationTokenFactory();
        final VerificationTokenValidator verificationTokenValidator = new VerificationTokenValidator();
        final VerificationTokenMapper verificationTokenMapper = new VerificationTokenMapper();
        final UserPromoter userPromoter = new UserPromoter(userManager, userMapper);
        final UserUpdater userUpdater = new UserUpdater(userManager, userMapper);
        final UserRegistration userRegistration = new UserRegistration(userFactory, userManager, userMapper);
        final VerificationRegistration verificationRegistration = new VerificationRegistration(
                userManager,
                userMapper,
                verificationTokenFactory,
                verificationTokenRepository,
                verificationTokenValidator,
                verificationTokenMapper);
        return new UserFacade(userPromoter, userUpdater, userRegistration, verificationRegistration, userManager, userMapper);
    }
}
