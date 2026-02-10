package com.alexsaldan.library.application.port.outbound;

import com.alexsaldan.library.domain.Book;

import java.util.List;

/**
 * Porta de saída para listar todos os livros.
 */
public interface ListBooksPort {

    List<Book> findAll();
}
