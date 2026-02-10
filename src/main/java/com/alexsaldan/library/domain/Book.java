package com.alexsaldan.library.domain;

import java.util.Objects;

/**
 * Entidade de domínio imutável que representa um livro.
 */
public class Book {

    private final Long id;
    private final String title;
    private final String author;

    // Construtor para novo livro (sem ID)
    public Book(String title, String author) {
        this(null, title, author);
    }

    // Construtor para livro existente (com ID)
    public Book(Long id, String title, String author) {
        validate(id, title, author);
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
    }

    private void validate(Long id, String title, String author) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Autor é obrigatório");
        }
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo");
        }
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
        return Objects.equals(title.toLowerCase(), book.title.toLowerCase()) &&
               Objects.equals(author.toLowerCase(), book.author.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase());
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "'}";
    }
}