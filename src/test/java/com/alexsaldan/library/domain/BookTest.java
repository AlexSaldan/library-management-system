package com.alexsaldan.library.domain;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes da entidade Book")
class BookTest {

    @Test
    @DisplayName("Deve criar livro com ID, título e autor válidos")
    void shouldCreateBookWithValidData() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin");
        
        assertEquals(1L, book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando título é nulo")
    void shouldThrowExceptionWhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(1L, null, "Author");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando título está vazio")
    void shouldThrowExceptionWhenTitleIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(1L, "", "Author");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor é nulo")
    void shouldThrowExceptionWhenAuthorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(1L, "Title", null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor está vazio")
    void shouldThrowExceptionWhenAuthorIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(1L, "Title", "");
        });
    }

    @Test
    @DisplayName("Deve remover espaços em branco do título e autor")
    void shouldTrimTitleAndAuthor() {
        Book book = new Book(1L, "  Clean Code  ", "  Robert C. Martin  ");
        
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID é inválido (negativo)")
    void shouldThrowExceptionWhenIdIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(-1L, "Title", "Author");
        });
    }

    @Test
    @DisplayName("Deve considerar livros iguais quando ID, título e autor são iguais")
    void shouldConsiderBooksEqualWhenAllFieldsMatch() {
        Book book1 = new Book(1L, "Clean Code", "Robert C. Martin");
        Book book2 = new Book(1L, "Clean Code", "Robert C. Martin");
        
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    @DisplayName("Deve considerar livros diferentes quando IDs são diferentes")
    void shouldConsiderBooksDifferentWhenIdsDiffer() {
        Book book1 = new Book(1L, "Clean Code", "Robert C. Martin");
        Book book2 = new Book(2L, "Clean Code", "Robert C. Martin");
        
        assertNotEquals(book1, book2);
    }
}

