package com.alexsaldan.library.application.usecase.book;

import com.alexsaldan.library.application.port.outbound.DeleteBookPort;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;

/**
 * Caso de uso para deletar livro.
 */
public class DeleteBookUseCase {

    private final GetBookByIdPort getBookByIdPort;
    private final DeleteBookPort deleteBookPort;

    public DeleteBookUseCase(GetBookByIdPort getBookByIdPort, DeleteBookPort deleteBookPort) {
        this.getBookByIdPort = getBookByIdPort;
        this.deleteBookPort = deleteBookPort;
    }

    public boolean execute(Long id) {
        if (getBookByIdPort.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Livro não encontrado com ID: " + id);
        }
        return deleteBookPort.deleteById(id);
    }
}