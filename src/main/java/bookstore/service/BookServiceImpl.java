package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.entity.Book;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.repository.BookRepositoryImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryImpl bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {

        Book newBook = bookMapper.toModel(createBookRequestDto);
        bookRepository.save(newBook);
        return bookMapper.toDto(newBook);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findBookById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity does`t exist with id: " + id));

        return bookMapper.toDto(book);
    }
}
