package bookstore.service;

import bookstore.entity.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
