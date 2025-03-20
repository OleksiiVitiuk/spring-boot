package bookstore.service;

import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto createBookRequestDto);

    void deleteBook(Long id);

    Page<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);
}
