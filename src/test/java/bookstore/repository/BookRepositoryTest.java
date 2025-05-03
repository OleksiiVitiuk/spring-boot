package bookstore.repository;

import bookstore.TestUtil;
import bookstore.entity.Book;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:database/book/add-category-for-book.sql",
        "classpath:database/book/add-book-to-table.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/book/delete-book-and-category.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Test find book by category id")
    public void findByCategoriesId_findOneBookByCategoryId_ReturnsOneBook() {
        Pageable pageable = PageRequest.of(0, 5);
        Long categoryId = 1L;
        int expectedSize = 5;
        int expectedNumElements = 1;

        Page<Book> actualBooks = bookRepository.findByCategoriesId(categoryId, pageable);

        assertNotNull(actualBooks);
        assertEquals(expectedSize, actualBooks.getSize());
        assertEquals(expectedNumElements, actualBooks.getTotalElements());

        assertBook(TestUtil.getBook(), actualBooks.getContent().get(0));
    }

    @Test
    @DisplayName("Test find books by filtering")
    public void findAll_findAllWithFilter_ReturnsOneBook() {
        int expectedNumElements = 1;
        String expectedTitle = "BookTitle";
        Specification<Book> spec = (root, query, cb) -> cb.like(root.get("title"),
                "%" + expectedTitle + "%");
        Pageable pageable = PageRequest.of(0, 2, Sort.by("title").ascending());

        Page<Book> actualBooks = bookRepository.findAll(spec, pageable);

        assertNotNull(actualBooks);
        assertFalse(actualBooks.isEmpty());
        assertEquals(expectedNumElements, actualBooks.getTotalElements());

        assertBook(TestUtil.getBook(), actualBooks.getContent().get(0));
    }

    private void assertBook(Book expected, Book actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(0, expected.getPrice().compareTo(actual.getPrice())); // Для BigDecimal
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCoverImage(), actual.getCoverImage());
        assertEquals(expected.getCategories().iterator().next().getId(),
                actual.getCategories().iterator().next().getId());
    }
}
