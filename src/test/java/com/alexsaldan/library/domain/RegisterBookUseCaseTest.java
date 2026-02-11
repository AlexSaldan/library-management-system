package com.alexsaldan.library.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do RegisterBookUseCase")
class RegisterBookUseCaseTest {

    @Mock
    private SaveBookPort saveBookPort;

    @InjectMocks
    private RegisterBookUseCase useCase;

    @Test
    @DisplayName("Deve registrar livro com sucesso")
    void shouldRegisterBookSuccessfully() {
        RegisterBookInput input = new RegisterBookInput("Clean Architecture", "Robert C. Martin");
        Book savedBook = new Book(1L, "Clean Architecture", "Robert C. Martin");
        
        when(saveBookPort.save(any(Book.class))).thenReturn(savedBook);

        RegisterBookOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(1L, output.id());
        assertEquals("Clean Architecture", output.title());
        assertEquals("Robert C. Martin", output.author());
        
        verify(saveBookPort, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando título é vazio")
    void shouldThrowExceptionWhenTitleIsEmpty() {
        RegisterBookInput input = new RegisterBookInput("", "Author");

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(input);
        });
        
        verify(saveBookPort, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor é vazio")
    void shouldThrowExceptionWhenAuthorIsEmpty() {
        RegisterBookInput input = new RegisterBookInput("Title", "");

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(input);
        });
        
        verify(saveBookPort, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve salvar livro com ID atribuído pelo repositório")
    void shouldSaveBookWithIdAssignedByRepository() {
        RegisterBookInput input = new RegisterBookInput("Domain-Driven Design", "Eric Evans");
        Book savedBook = new Book(42L, "Domain-Driven Design", "Eric Evans");
        
        when(saveBookPort.save(any(Book.class))).thenReturn(savedBook);

        RegisterBookOutput output = useCase.execute(input);

        assertEquals(42L, output.id());
        verify(saveBookPort, times(1)).save(any(Book.class));
    }
}
