package com.alexsaldan.library.application.config;

import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterBookUseCase registerBookUseCase(SaveBookPort saveBookPort) {
        return new RegisterBookUseCase(saveBookPort);
    }
}
