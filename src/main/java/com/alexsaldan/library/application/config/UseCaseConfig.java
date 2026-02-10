package com.alexsaldan.library.application.config;

import com.alexsaldan.library.application.port.outbound.*;
import com.alexsaldan.library.application.usecase.book.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterBookUseCase registerBookUseCase(SaveBookPort saveBookPort) {
        return new RegisterBookUseCase(saveBookPort);
    }

    @Bean
    public UpdateBookUseCase updateBookUseCase(GetBookByIdPort getBookByIdPort, SaveBookPort saveBookPort) {
        return new UpdateBookUseCase(getBookByIdPort, saveBookPort);
    }

    @Bean
    public DeleteBookUseCase deleteBookUseCase(GetBookByIdPort getBookByIdPort, DeleteBookPort deleteBookPort) {
        return new DeleteBookUseCase(getBookByIdPort, deleteBookPort);
    }
}