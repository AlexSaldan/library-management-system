package com.alexsaldan.library.application.usecase.book;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.domain.Book;

/**
 * Caso de uso: registrar um novo livro (ADR-001).
 * Orquestra a regra de negócio e delega persistência à porta.
 */
public class RegisterBookUseCase {

    private final SaveBookPort saveBookPort;

    public RegisterBookUseCase(SaveBookPort saveBookPort) {
        this.saveBookPort = saveBookPort;
    }

    public RegisterBookOutput execute(RegisterBookInput input) {
        Book book = new Book(null, input.title(), input.author());
        Book saved = saveBookPort.save(book);
        return new RegisterBookOutput(saved.getId(), saved.getTitle(), saved.getAuthor());
    }
}
