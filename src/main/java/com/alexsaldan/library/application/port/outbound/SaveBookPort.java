package com.alexsaldan.library.application.port.outbound;

import com.alexsaldan.library.domain.Book;

/**
 * Porta de saída para persistência de livros.
 * A implementação fica na camada de infraestrutura.
 */
public interface SaveBookPort {

    /**
     * Persiste o livro e retorna a instância com id atribuído.
     */
    Book save(Book book);
}
