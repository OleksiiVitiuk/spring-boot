package main.java.bookstore.repository;

import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public class BookRepositoryImpl {

    private final BookRepository bookRepository;

    public BookRepositoryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
