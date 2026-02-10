package com.alexsaldan.library.infrastructure.persistence;

import com.alexsaldan.library.application.port.outbound.DeleteBookPort;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.ListBooksPort;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação em memória das portas de saída de livros.
 * Única fonte de verdade para leitura e escrita enquanto não há banco.
 */
@Repository
public class InMemoryBookRepository implements SaveBookPort, ListBooksPort, GetBookByIdPort, DeleteBookPort {

    private final List<Book> books = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public InMemoryBookRepository() {
        books.add(new Book(nextId.getAndIncrement(), "O Senhor dos Anéis", "J.R.R. Tolkien"));
        books.add(new Book(nextId.getAndIncrement(), "1984", "George Orwell"));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId.getAndIncrement());
            books.add(book);
            return book;
        }

        books.removeIf(b -> b.getId().equals(book.getId()));
        books.add(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return books.removeIf(b -> b.getId().equals(id));
    }
}

