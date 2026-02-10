package com.alexsaldan.library.application.port.outbound;

import com.alexsaldan.library.domain.Book;

import java.util.Optional;

/**
 * Porta de saída para buscar um livro por id.
 */
public interface GetBookByIdPort {

    Optional<Book> findById(Long id);
}
