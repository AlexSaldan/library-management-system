package com.alexsaldan.library.domain;

import java.util.Objects;

/**
 * Entidade de domínio que representa um livro.
 * Sem dependências de framework (ADR-001, Clean Architecture).
 */
public class Book {

    private final Long id;
    private final String title;
    private final String author;

    public Book(Long id, String title, String author) {
        // Validações obrigatórias
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser vazio");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor não pode ser vazio");
        }
        
        // Validação de ID: só valida se não for null (livros novos podem ter ID null)
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo");
        }
        
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        
        // Se ambos têm ID válido, comparar só pelo ID (identidade de entidade)
        if (this.id != null && book.id != null) {
            return Objects.equals(this.id, book.id);
        }
        
        // Se um ou ambos não têm ID, comparar por título + autor (evitar duplicatas)
        return Objects.equals(this.title.toLowerCase(), book.title.toLowerCase()) &&
               Objects.equals(this.author.toLowerCase(), book.author.toLowerCase());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return Objects.hash(title.toLowerCase(), author.toLowerCase());
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "'}";
    }
}