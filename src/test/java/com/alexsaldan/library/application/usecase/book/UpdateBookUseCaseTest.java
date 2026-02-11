package com.alexsaldan.library.application.usecase.book;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.domain.Book;
import com.alexsaldan.library.exception.BookNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UpdateBookUseCase")
class UpdateBookUseCaseTest {

    @Mock
    private GetBookByIdPort getBookByIdPort;

    @Mock
    private SaveBookPort saveBookPort;

    @InjectMocks
    private UpdateBookUseCase useCase;

    @Test
    @DisplayName("Deve atualizar livro com sucesso quando livro existe")
    void shouldUpdateBookSuccessfullyWhenBookExists() {
        Long bookId = 1L;
        RegisterBookInput input = new RegisterBookInput("Novo Título", "Novo Autor");
        Book existingBook = new Book(bookId, "Título Antigo", "Autor Antigo");
        Book updatedBook = new Book(bookId, "Novo Título", "Novo Autor");
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(saveBookPort.save(any(Book.class))).thenReturn(updatedBook);

        RegisterBookOutput output = useCase.execute(bookId, input);

        assertNotNull(output);
        assertEquals(bookId, output.id());
        assertEquals("Novo Título", output.title());
        assertEquals("Novo Autor", output.author());
        
        verify(getBookByIdPort, times(1)).findById(bookId);
        verify(saveBookPort, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar BookNotFoundException quando livro não existe")
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        Long bookId = 999L;
        RegisterBookInput input = new RegisterBookInput("Título", "Autor");
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            useCase.execute(bookId, input);
        });
        
        verify(getBookByIdPort, times(1)).findById(bookId);
        verify(saveBookPort, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve atualizar apenas os campos fornecidos")
    void shouldUpdateOnlyProvidedFields() {
        Long bookId = 1L;
        RegisterBookInput input = new RegisterBookInput("Título Atualizado", "Autor Atualizado");
        Book existingBook = new Book(bookId, "Título Velho", "Autor Velho");
        Book updatedBook = new Book(bookId, "Título Atualizado", "Autor Atualizado");
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(saveBookPort.save(any(Book.class))).thenReturn(updatedBook);

        RegisterBookOutput output = useCase.execute(bookId, input);

        assertEquals("Título Atualizado", output.title());
        assertEquals("Autor Atualizado", output.author());
    }
}
