package com.alexsaldan.library.infrastructure.web;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.ListBooksPort;
import com.alexsaldan.library.application.usecase.book.DeleteBookUseCase;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;
import com.alexsaldan.library.application.usecase.book.UpdateBookUseCase;
import com.alexsaldan.library.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final ListBooksPort listBooksPort;      // Mantido para leitura simples
    private final GetBookByIdPort getBookByIdPort;  // Mantido para leitura simples

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
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(listBooksPort.findAll());
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            boolean deleted = deleteBookUseCase.execute(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

