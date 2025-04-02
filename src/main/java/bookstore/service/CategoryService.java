package bookstore.service;

import bookstore.dto.book.BookDtoWithoutCategoryIds;
import bookstore.dto.category.CategoryCreateDto;
import bookstore.dto.category.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto saveCategory(CategoryCreateDto categoryCreateDto);

    CategoryDto update(Long id, CategoryCreateDto categoryCreateDto);

    CategoryDto getById(Long id);

    void deleteCategory(Long id);

    Page<BookDtoWithoutCategoryIds> findBooksByCategory(Pageable pageable, Long id);
}
