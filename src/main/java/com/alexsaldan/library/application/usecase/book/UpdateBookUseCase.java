package com.alexsaldan.library.application.usecase.book;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.domain.Book;
import com.alexsaldan.library.exception.BookNotFoundException;

/**
 * Caso de uso para atualizar um livro existente.
 * Garante que o livro exista antes de atualizar (regra de negócio).
 */
public class UpdateBookUseCase {

    private final GetBookByIdPort getBookByIdPort;
    private final SaveBookPort saveBookPort;

    public UpdateBookUseCase(GetBookByIdPort getBookByIdPort, SaveBookPort saveBookPort) {
        this.getBookByIdPort = getBookByIdPort;
        this.saveBookPort = saveBookPort;
    }

    public RegisterBookOutput execute(Long id, RegisterBookInput input) {
        // Regra de negócio: livro deve existir para ser atualizado
        Book existing = getBookByIdPort.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        // Cria nova versão do livro com dados atualizados
        Book updated = new Book(existing.getId(), input.title(), input.author());
        
        // Persiste
        Book saved = saveBookPort.save(updated);
        
        return new RegisterBookOutput(saved.getId(), saved.getTitle(), saved.getAuthor());
    }
}
