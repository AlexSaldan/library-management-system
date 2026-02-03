package com.alexsaldan.library.domain;

/**
 * Entidade de domínio que representa um livro.
 * Sem dependências de framework (ADR-001, Clean Architecture).
 */
public class Book {

    private Long id;
    private final String title;
    private final String author;

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
