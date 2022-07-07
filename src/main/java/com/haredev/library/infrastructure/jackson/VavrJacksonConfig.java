package com.haredev.library.infrastructure.jackson;

import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VavrJacksonConfig {
    @Bean
    Module vavrModule() {
        return new VavrModule();
    }
}
