package main.java.bookstore.service;

import java.awt.*;
import java.awt.print.Book;

public interface BookService {
    Book save(Book book);
    List<Book> findAll();
}
