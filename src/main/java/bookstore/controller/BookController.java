package bookstore.controller;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books", description = "API for book management")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Returns a paginated list of books")
    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Get book by ID", description = "Returns detailed information about a book by its ID")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Create a book", description = "Adds a new book to the system")
    @PostMapping
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @Operation(summary = "Update the book", description = "Updates information about a book by its ID")
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody CreateBookRequestDto createBookRequestDto) {
        return bookService.updateBook(id, createBookRequestDto);
    }

    @Operation(summary = "Delete the book", description = "Logical deletion of a book by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @Operation(summary = "Search for books", description = "Returns a list of books that match the search criteria")
    @GetMapping("/search")
    public Page<BookDto> searchBooks(BookSearchParametersDto searchParameters,
                                     Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
