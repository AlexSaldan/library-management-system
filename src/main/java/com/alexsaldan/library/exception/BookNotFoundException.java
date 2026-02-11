package com.alexsaldan.library.exception;

/**
 * Exceção lançada quando um livro não é encontrado.
 */
public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public BookNotFoundException(Long id) {
        super("Livro com ID " + id + " não encontrado");
    }
}
