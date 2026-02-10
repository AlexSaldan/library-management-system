package com.alexsaldan.library.infrastructure.web;

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.outbound.DeleteBookPort;
import com.alexsaldan.library.application.port.outbound.GetBookByIdPort;
import com.alexsaldan.library.application.port.outbound.ListBooksPort;
import com.alexsaldan.library.application.port.outbound.SaveBookPort;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;
import com.alexsaldan.library.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Adaptador HTTP para recursos de livro.
 * Não contém regra de negócio, apenas delega para use cases e portas.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final ListBooksPort listBooksPort;
    private final GetBookByIdPort getBookByIdPort;
    private final SaveBookPort saveBookPort;
    private final DeleteBookPort deleteBookPort;

    public BookController(RegisterBookUseCase registerBookUseCase,
                          ListBooksPort listBooksPort,
                          GetBookByIdPort getBookByIdPort,
                          SaveBookPort saveBookPort,
                          DeleteBookPort deleteBookPort) {
        this.registerBookUseCase = registerBookUseCase;
        this.listBooksPort = listBooksPort;
        this.getBookByIdPort = getBookByIdPort;
        this.saveBookPort = saveBookPort;
        this.deleteBookPort = deleteBookPort;
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
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody RegisterBookInput input) {
        return getBookByIdPort.findById(id)
                .map(existing -> {
                    Book updated = new Book(existing.getId(), input.title(), input.author());
                    Book saved = saveBookPort.save(updated);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = deleteBookPort.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

