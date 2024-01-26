package com.haredev.library.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {
    UserFacade userFacade() { return userFacade(new InMemoryUserRepository()); }

    @Bean
    UserFacade userFacade(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserValidation userValidation = new UserValidation(userRepository);
        UserManager userManager = new UserManager(userRepository, userValidation, passwordEncoder);
        return new UserFacade(userManager);
    }
}
