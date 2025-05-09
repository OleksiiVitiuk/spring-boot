package bookstore;

import bookstore.dto.book.CreateBookRequestDto;
import bookstore.dto.category.CategoryCreateDto;
import bookstore.entity.Book;
import bookstore.entity.Category;
import java.math.BigDecimal;
import java.util.Set;

public class TestUtil {
    private static final CreateBookRequestDto createBookRequestDto
            = new CreateBookRequestDto();
    private static final CategoryCreateDto categoryCreateDto
            = new CategoryCreateDto();
    private static final Category category = new Category();
    private static final Book book = new Book();

    public static CategoryCreateDto getCategoryCreateDto() {
        categoryCreateDto.setName("Fantasy");
        categoryCreateDto.setDescription("Interesting fantastic stories");
        return categoryCreateDto;
    }

    public static CreateBookRequestDto getRequestToUpdateBook() {
        createBookRequestDto.setTitle("Updated Title");
        createBookRequestDto.setAuthor("Updated Author");
        createBookRequestDto.setIsbn("123-456-789");
        createBookRequestDto.setPrice(BigDecimal.valueOf(9.99));
        createBookRequestDto.setDescription("Updated Description");
        createBookRequestDto.setCoverImage("https://prnt.sc/fuAknzzANREq");
        createBookRequestDto.setCategoryIds(Set.of(1L));
        return createBookRequestDto;
    }

    public static Category getCategory() {
        category.setId(1L);
        category.setName("Fantasy");
        category.setDescription(
                "Interesting fantastic stories");
        return category;
    }

    public static Book getBook() {
        book.setId(1L);
        book.setTitle("BookTitle");
        book.setAuthor("BookAuthor");
        book.setIsbn("123-456-789");
        book.setPrice(BigDecimal.valueOf(20.00));
        book.setDescription("desc");
        book.setCoverImage("path");
        book.setCategories(Set.of(getCategory()));
        return book;
    }
}
