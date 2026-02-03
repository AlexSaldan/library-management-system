package com.alexsaldan.library.application.dto.book;

/**
 * DTO de saída do caso de uso de registro de livro.
 */
public record RegisterBookOutput(Long id, String title, String author) {}
