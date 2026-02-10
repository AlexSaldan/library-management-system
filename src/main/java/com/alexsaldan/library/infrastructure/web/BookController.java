package com.alexsaldan.library.infrastructure.web;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.ListBooksPort;
import com.alexsaldan.library.application.usecase.book.DeleteBookUseCase;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;
import com.alexsaldan.library.application.usecase.book.UpdateBookUseCase;
import com.alexsaldan.library.domain.Book;
import com.alexsaldan.library.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adaptador HTTP para recursos de livro.
 * Não contém regra de negócio, apenas delega para use cases e portas.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final ListBooksPort listBooksPort;
    private final GetBookByIdPort getBookByIdPort;

    public BookController(RegisterBookUseCase registerBookUseCase,
                          UpdateBookUseCase updateBookUseCase,
                          DeleteBookUseCase deleteBookUseCase,
                          ListBooksPort listBooksPort,
                          GetBookByIdPort getBookByIdPort) {
        this.registerBookUseCase = registerBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.listBooksPort = listBooksPort;
        this.getBookByIdPort = getBookByIdPort;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return listBooksPort.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return getBookByIdPort.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegisterBookOutput> createBook(@RequestBody RegisterBookInput input) {
        RegisterBookOutput output = registerBookUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegisterBookOutput> updateBook(@PathVariable Long id, @RequestBody RegisterBookInput input) {
        try {
            RegisterBookOutput output = updateBookUseCase.execute(id, input);
            return ResponseEntity.ok(output);
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            deleteBookUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

