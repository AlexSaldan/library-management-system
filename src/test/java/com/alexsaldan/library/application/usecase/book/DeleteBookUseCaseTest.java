package com.alexsaldan.library.application.usecase.book;


import com.alexsaldan.library.application.port.outbound.DeleteBookPort;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.domain.Book;
import com.alexsaldan.library.exception.BookNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do DeleteBookUseCase")
class DeleteBookUseCaseTest {

    @Mock
    private GetBookByIdPort getBookByIdPort;

    @Mock
    private DeleteBookPort deleteBookPort;

    @InjectMocks
    private DeleteBookUseCase useCase;

    @Test
    @DisplayName("Deve deletar livro com sucesso quando livro existe")
    void shouldDeleteBookSuccessfullyWhenBookExists() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book(bookId, "Título Existente", "Autor Existente");
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(deleteBookPort.deleteById(bookId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> {
            useCase.execute(bookId);
        });
        
        verify(getBookByIdPort, times(1)).findById(bookId);
        verify(deleteBookPort, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Deve lançar BookNotFoundException quando livro não existe")
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        // Arrange
        Long bookId = 999L;
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> {
            useCase.execute(bookId);
        });
        
        verify(getBookByIdPort, times(1)).findById(bookId);
        verify(deleteBookPort, never()).deleteById(bookId);
    }

    @Test
    @DisplayName("Deve verificar existência antes de tentar deletar")
    void shouldCheckExistenceBeforeDeleting() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book(bookId, "Outro Livro", "Outro Autor");
        
        when(getBookByIdPort.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(deleteBookPort.deleteById(bookId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> {
            useCase.execute(bookId);
        });

        // Assert
        verify(getBookByIdPort, times(1)).findById(bookId);
        verify(deleteBookPort, times(1)).deleteById(bookId);
    }
}

