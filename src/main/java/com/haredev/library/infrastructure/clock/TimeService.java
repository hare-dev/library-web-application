package com.haredev.library.infrastructure.clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ClockConfiguration {
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
