package bookstore;

import bookstore.entity.Book;
import bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {
    private final BookService bookService;

    @Autowired
    public BookstoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setAuthor("Test Author");
            book.setTitle("First book");
            book.setIsbn("ISBN");
            book.setPrice(BigDecimal.valueOf(100));
            book.setDescription("Interesting book");
            book.setCoverImage("https://edit.org/images/cat/book-covers-big-2019101610.jpg");
            bookService.save(book);
            bookService.findAll().forEach(System.out::println);
        };
    }
}
