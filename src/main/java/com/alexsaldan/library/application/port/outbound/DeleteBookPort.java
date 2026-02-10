package com.alexsaldan.library.application.port.outbound;

/**
 * Porta de saída para remoção de livro por id.
 */
public interface DeleteBookPort {

    boolean deleteById(Long id);
}
