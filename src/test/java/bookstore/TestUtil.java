package bookstore;

import bookstore.dto.book.CreateBookRequestDto;
import bookstore.dto.category.CategoryCreateDto;
import bookstore.entity.Book;
import bookstore.entity.Category;
import java.math.BigDecimal;
import java.util.Set;

public class TestUtil {
    private static final CreateBookRequestDto CREATE_BOOK_REQUEST_DTO
            = new CreateBookRequestDto();
    private static final CategoryCreateDto CATEGORY_CREATE_DTO
            = new CategoryCreateDto();
    private static final Category CATEGORY = new Category();
    private static final Book BOOK = new Book();

    public static CategoryCreateDto getCategoryCreateDto() {
        CATEGORY_CREATE_DTO.setName("Fantasy");
        CATEGORY_CREATE_DTO.setDescription("Interesting fantastic stories");
        return CATEGORY_CREATE_DTO;
    }

    public static CreateBookRequestDto getRequestToUpdateBook() {
        CREATE_BOOK_REQUEST_DTO.setTitle("Updated Title");
        CREATE_BOOK_REQUEST_DTO.setAuthor("Updated Author");
        CREATE_BOOK_REQUEST_DTO.setIsbn("123-456-789");
        CREATE_BOOK_REQUEST_DTO.setPrice(BigDecimal.valueOf(9.99));
        CREATE_BOOK_REQUEST_DTO.setDescription("Updated Description");
        CREATE_BOOK_REQUEST_DTO.setCoverImage("https://prnt.sc/fuAknzzANREq");
        CREATE_BOOK_REQUEST_DTO.setCategoryIds(Set.of(1L));
        return CREATE_BOOK_REQUEST_DTO;
    }

    public static Category getCategory() {
        CATEGORY.setId(1L);
        CATEGORY.setName("Fantasy");
        CATEGORY.setDescription(
                "Interesting fantastic stories");
        return CATEGORY;
    }

    public static Book getBook() {
        BOOK.setId(1L);
        BOOK.setTitle("BookTitle");
        BOOK.setAuthor("BookAuthor");
        BOOK.setIsbn("123-456-789");
        BOOK.setPrice(BigDecimal.valueOf(20.00));
        BOOK.setDescription("desc");
        BOOK.setCoverImage("path");
        BOOK.setCategories(Set.of(getCategory()));
        return BOOK;
    }
}
