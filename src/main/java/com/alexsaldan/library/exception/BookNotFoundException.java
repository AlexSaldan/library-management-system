package com.alexsaldan.library.exception;

/**
 * Exceção lançada quando um livro não é encontrado.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Livro com ID " + id + " não encontrado");
    }
}
