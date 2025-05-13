package bookstore.service;

import bookstore.dto.category.CategoryCreateDto;
import bookstore.dto.category.CategoryDto;
import bookstore.entity.Category;
import bookstore.mapper.CategoryMapper;
import bookstore.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryCreateDto categoryCreateDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Some name");
        category.setDescription("Some description");

        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Some name");
        categoryDto.setDescription("Some description");

        categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Some name");
        categoryCreateDto.setDescription("Some description");
    }

    @Test
    @DisplayName("Find all categories")
    void findAll_ShouldReturnPageOfCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categories = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categories);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        Page<CategoryDto> result = categoryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Some name", result.getContent().get(0).getName());

        verify(categoryRepository).findAll(pageable);
        verify(categoryMapper).toDto(category);
    }

    @Test
    @DisplayName("Save category")
    void saveCategory_ShouldReturnSavedCategory() {
        when(categoryMapper.toModel(categoryCreateDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.saveCategory(categoryCreateDto);

        assertNotNull(result);
        assertEquals("Some name", result.getName());

        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(category);
    }

    @Test
    @DisplayName("Update category")
    void updateCategory_ShouldReturnUpdatedCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.update(1L, categoryCreateDto);

        assertNotNull(result);
        assertEquals("Some name", result.getName());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(category);
    }

    @Test
    @DisplayName("Find all categories when there are no categories")
    void findAll_ShouldReturnEmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categories = new PageImpl<>(List.of());

        when(categoryRepository.findAll(pageable)).thenReturn(categories);

        Page<CategoryDto> result = categoryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(categoryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Save category when save operation fails")
    void saveCategory_ShouldThrowException_WhenSaveFails() {
        when(categoryMapper.toModel(categoryCreateDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoryService.saveCategory(categoryCreateDto));

        assertEquals("Database error", exception.getMessage());

        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Update category when category does not exist")
    void updateCategory_ShouldThrowException_WhenCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            categoryService.update(1L, categoryCreateDto);
        } catch (RuntimeException e) {
            assertEquals("Category is not found with id: 1", e.getMessage());
        }

        verify(categoryRepository).findById(1L);
    }

    @Test
    @DisplayName("Save category with invalid data")
    void saveCategory_ShouldThrowException_WhenDataIsInvalid() {
        categoryCreateDto.setName(null);

        try {
            categoryService.saveCategory(categoryCreateDto);
        } catch (IllegalArgumentException e) {
            assertEquals("Category name cannot be null", e.getMessage());
        }
    }
}
