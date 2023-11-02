package bg.softuni.booksserver.web;

import bg.softuni.booksserver.model.dto.BookDTO;
import bg.softuni.booksserver.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable Long id) {

        Optional<BookDTO> bookDTOOptional = bookService.findBookById(id);

        /*
        if (bookDTOOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResposeEntity.ok(bookDTOOptional.get());
        }
        */

        return bookDTOOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestBody BookDTO bookDTO,
            UriComponentsBuilder uriComponentsBuilder) {

        long newBookId = bookService.createBook(bookDTO);

        return ResponseEntity.created(uriComponentsBuilder.path("/api/books/{id}").build(newBookId)).build();

    }
}
