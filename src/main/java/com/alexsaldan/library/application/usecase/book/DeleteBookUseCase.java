package com.alexsaldan.library.application.usecase.book;

import com.alexsaldan.library.application.port.outbound.DeleteBookPort;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.exception.BookNotFoundException;

/**
 * Caso de uso para deletar um livro por ID.
 * Verifica existência antes da remoção (regra de negócio).
 */
public class DeleteBookUseCase {

    private final GetBookByIdPort getBookByIdPort;
    private final DeleteBookPort deleteBookPort;

    public DeleteBookUseCase(GetBookByIdPort getBookByIdPort, DeleteBookPort deleteBookPort) {
        this.getBookByIdPort = getBookByIdPort;
        this.deleteBookPort = deleteBookPort;
    }

    public void execute(Long id) {
        if (!getBookByIdPort.findById(id).isPresent()) {
            throw new BookNotFoundException(id);
        }
        deleteBookPort.deleteById(id);
    }
}
