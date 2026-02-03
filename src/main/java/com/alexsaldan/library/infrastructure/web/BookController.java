package com.alexsaldan.library.infrastructure.web;

import java.util.List;

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

import com.alexsaldan.library.application.dto.book.RegisterBookInput;
import com.alexsaldan.library.application.dto.book.RegisterBookOutput;
import com.alexsaldan.library.application.port.out.DeleteBookPort;
import com.alexsaldan.library.application.port.out.GetBookByIdPort;
import com.alexsaldan.library.application.port.out.ListBooksPort;
import com.alexsaldan.library.application.port.out.SaveBookPort;
import com.alexsaldan.library.application.usecase.book.RegisterBookUseCase;
import com.alexsaldan.library.domain.Book;

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
        var output = registerBookUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody RegisterBookInput input) {
        return getBookByIdPort.findById(id)
                .map(book -> {
                    Book updated = new Book(book.getId(), input.title(), input.author());
                    return ResponseEntity.ok(saveBookPort.save(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return deleteBookPort.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
