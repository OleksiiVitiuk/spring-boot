package main.java.bookstore.service;

import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryImpl bookRepository;

    public BookServiceImpl(BookRepositoryImpl bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
