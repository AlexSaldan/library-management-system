package com.alexsaldan.library.infrastructure.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.alexsaldan.library.domain.Book;

@DisplayName("Testes do InMemoryBookRepository")
class InMemoryBookRepositoryTest {

    private InMemoryBookRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBookRepository();
    }

    @Test
    @DisplayName("Deve salvar novo livro e atribuir ID")
    void shouldSaveNewBookAndAssignId() {
        Book book = new Book(null, "Test Book", "Test Author");

        Book saved = repository.save(book);

        assertNotNull(saved.getId());
        assertEquals("Test Book", saved.getTitle());
        assertEquals("Test Author", saved.getAuthor());
    }

    @Test
    @DisplayName("Deve retornar todos os livros")
    void shouldReturnAllBooks() {
        List<Book> books = repository.findAll();

        assertNotNull(books);
        assertTrue(books.size() >= 2);
    }

    @Test
    @DisplayName("Deve encontrar livro por ID")
    void shouldFindBookById() {
        Book book = new Book(null, "Test", "Author");
        Book saved = repository.save(book);

        Optional<Book> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Test", found.get().getTitle());
    }

    @Test
    @DisplayName("Deve retornar vazio quando livro não encontrado")
    void shouldReturnEmptyWhenBookNotFound() {
        Optional<Book> found = repository.findById(9999L);

        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Deve atualizar livro existente")
    void shouldUpdateExistingBook() {
        Book book = new Book(null, "Old Title", "Old Author");
        Book saved = repository.save(book);

        Book updated = new Book(saved.getId(), "New Title", "New Author");
        Book result = repository.save(updated);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
    }

    @Test
    @DisplayName("Deve deletar livro por ID")
    void shouldDeleteBookById() {
        Book book = new Book(null, "To Delete", "Author");
        Book saved = repository.save(book);

        boolean deleted = repository.deleteById(saved.getId());

        assertTrue(deleted);
        assertFalse(repository.findById(saved.getId()).isPresent());
    }

    @Test
    @DisplayName("Deve retornar falso quando tenta deletar livro inexistente")
    void shouldReturnFalseWhenDeletingNonExistentBook() {
        boolean deleted = repository.deleteById(9999L);

        assertFalse(deleted);
    }

    @Test
    @DisplayName("Deve manter dados iniciais após inicialização")
    void shouldKeepInitialDataAfterInitialization() {
        List<Book> books = repository.findAll();

        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("O Senhor dos Anéis")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("1984")));
    }
}
