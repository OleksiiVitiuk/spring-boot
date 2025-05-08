package bookstore.controller;

import bookstore.TestUtil;
import bookstore.dto.book.BookDto;
import bookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
@Sql(scripts = {
        "classpath:database/book/add-category-for-book.sql",
        "classpath:database/book/add-book-to-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/book/delete-book-and-category.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test get book by id")
    void testGetBookById() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertBook(actual,
                1L,
                "BookTitle", "BookAuthor",
                "123-456-789",
                BigDecimal.valueOf(20.00),
                "desc", "path");
    }

    @Test
    @DisplayName("Test update book")
    void updateBook() throws Exception {
        String json = objectMapper.writeValueAsString(TestUtil.getRequestToUpdateBook());

        MvcResult result = mockMvc.perform(put("/books/1")
                        .content(json)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertBook(actual,
                1L,
                "Updated Title",
                "Updated Author",
                "123-456-789",
                BigDecimal.valueOf(9.99),
                "Updated Description",
                "https://prnt.sc/fuAknzzANREq");
    }

    @Test
    @DisplayName("Test search books")
    void searchBooks() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("title", "BookTitle");
        requestParams.add("author", "BookAuthor");
        requestParams.add("isbn", "123-456-789");

        MvcResult result = mockMvc.perform(get("/books/search")
                        .params(requestParams)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode contentNode = objectMapper.readTree(
                result.getResponse().getContentAsString()).get("content");
        List<BookDto> books = objectMapper.readValue(
                contentNode.toString(), new TypeReference<>() {});

        assertFalse(books.isEmpty());
        assertBook(books.get(0), 1L,
                "BookTitle", "BookAuthor",
                "123-456-789",
                BigDecimal.valueOf(20.00),
                "desc", "path");
    }

    @Test
    @DisplayName("Test invalid book ISBN")
    void validBookTest() throws Exception {
        CreateBookRequestDto invalidBook = TestUtil.getRequestToUpdateBook();
        invalidBook.setIsbn("12345");
        String json = objectMapper.writeValueAsString(invalidBook);

        MvcResult result = mockMvc.perform(put("/books/1")
                        .content(json)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Invalid ISBN format"));
    }

    private void assertBook(BookDto actualBook,
                            Long id,
                            String title,
                            String author,
                            String isbn,
                            BigDecimal price,
                            String description,
                            String coverImage) {
        assertNotNull(actualBook);
        assertEquals(id, actualBook.getId());
        assertEquals(title, actualBook.getTitle());
        assertEquals(author, actualBook.getAuthor());
        assertEquals(isbn, actualBook.getIsbn());
        assertTrue(price.compareTo(actualBook.getPrice()) == 0);
        assertEquals(description, actualBook.getDescription());
        assertEquals(coverImage, actualBook.getCoverImage());
    }
}
