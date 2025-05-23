package bookstore.service;

import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.entity.Book;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.repository.book.BookRepository;
import bookstore.repository.book.BookSpecificationBuilder;
import bookstore.util.TestUtil;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get book by id")
    void getBookById_ShouldReturnBookDto() {
        Book book = TestUtil.getBook();
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setTitle(book.getTitle());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.getBookById(book.getId());

        assertEquals(expectedBookDto, actualBookDto);
        verify(bookRepository).findById(book.getId());
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("Update book")
    void updateBook_ShouldReturnUpdatedBookDto() {
        Book book = TestUtil.getBook();
        CreateBookRequestDto requestDto = TestUtil.getRequestToUpdateBook();
        Book updatedBook = TestUtil.getBook();
        updatedBook.setTitle(requestDto.getTitle());
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setTitle(requestDto.getTitle());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedBookDto);

        BookDto actualBookDto = bookService.updateBook(book.getId(), requestDto);

        assertEquals(expectedBookDto, actualBookDto);
        verify(bookRepository).findById(book.getId());
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(updatedBook);
    }

    @Test
    @DisplayName("Search books by title")
    void searchBooks_ShouldReturnFilteredBooks() {
        Book book = TestUtil.getBook();
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        Page<Book> bookPage = new PageImpl<>(List.of(book));
        Page<BookDto> expectedPage = new PageImpl<>(List.of(bookDto));

        Specification<Book> spec = mock(Specification.class);
        when(bookSpecificationBuilder.build(any(BookSearchParametersDto.class))).thenReturn(spec);
        when(bookRepository.findAll(spec, Pageable.unpaged())).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookSearchParametersDto searchDto
                = new BookSearchParametersDto("BookTitle", null, null);

        // When
        Page<BookDto> actualPage = bookService.search(searchDto, Pageable.unpaged());

        // Then
        assertEquals(expectedPage.getContent(), actualPage.getContent());
        verify(bookSpecificationBuilder).build(any(BookSearchParametersDto.class));
        verify(bookRepository).findAll(spec, Pageable.unpaged());
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("Get book by id - exception(not found)")
    void getBookById_ShouldThrowException_WhenNotFound() {
        Long bookId = 99L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getBookById(bookId)
        );

        assertEquals("Failed to find book by ID: " + bookId, exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    @DisplayName("Update book - exception(not found)")
    void updateBook_ShouldThrowException_WhenBookNotFound() {
        Long bookId = 100L;
        CreateBookRequestDto requestDto = TestUtil.getRequestToUpdateBook();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.updateBook(bookId, requestDto)
        );

        assertEquals("Book not found by ID: " + bookId, exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    @DisplayName("Search books - empty list")
    void searchBooks_ShouldReturnEmptyList_WhenNoBooksMatch() {
        BookSearchParametersDto searchDto = new BookSearchParametersDto("NonExistentTitle", null, null);
        Specification<Book> spec = mock(Specification.class);

        when(bookSpecificationBuilder.build(searchDto)).thenReturn(spec);
        when(bookRepository.findAll(spec, Pageable.unpaged())).thenReturn(Page.empty());

        Page<BookDto> result = bookService.search(searchDto, Pageable.unpaged());

        assertTrue(result.isEmpty());
        verify(bookSpecificationBuilder).build(searchDto);
        verify(bookRepository).findAll(spec, Pageable.unpaged());
    }
}
