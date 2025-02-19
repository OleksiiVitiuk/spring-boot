package main.java.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(BookService bookService) {
        return args -> {
            bookService.save(new Book("The Hobbit", "J.R.R. Tolkien", "1234567890", new BigDecimal("10.99"), "Fantasy novel", "hobbit.jpg"));
            bookService.save(new Book("1984", "George Orwell", "0987654321", new BigDecimal("8.99"), "Dystopian novel", "1984.jpg"));

            List<Book> books = bookService.findAll();
            books.forEach(book -> System.out.println(book.getTitle() + " by " + book.getAuthor()));
        };
    }
}
