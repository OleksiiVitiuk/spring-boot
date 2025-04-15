package bookstore.controller;

import bookstore.dto.book.BookDtoWithoutCategoryIds;
import bookstore.dto.category.CategoryCreateDto;
import bookstore.dto.category.CategoryDto;
import bookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for CRUD categories and get books by category")
public class CategoryController {
    private CategoryService categoryService;

    @Operation(summary = "Get all categories",
            description = "Returns a paginated list of categories")
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Page<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Operation(summary = "Create a new category",
            description = "Creates and returns a new category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return categoryService.saveCategory(categoryCreateDto);
    }

    @Operation(summary = "Update an existing category",
            description = "Updates a category by its ID and returns the updated category")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return categoryService.update(id, categoryCreateDto);
    }

    @Operation(summary = "Get a category by ID",
            description = "Retrieves a category by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public CategoryDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Delete a category", description = "Deletes a category by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @Operation(summary = "Get books by category",
            description = "Returns a paginated list of books belonging to the specified category")
    @GetMapping("/{id}/books")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<BookDtoWithoutCategoryIds> getBooksByCategory(Pageable pageable,
                                                              @PathVariable Long id) {
        return categoryService.findBooksByCategory(pageable, id);
    }
}
