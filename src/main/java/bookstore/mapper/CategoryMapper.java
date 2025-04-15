package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.category.CategoryCreateDto;
import bookstore.dto.category.CategoryDto;
import bookstore.entity.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CategoryCreateDto categoryDto);

    @Mapping(target = "id", ignore = true)
    void updateCategory(CategoryCreateDto categoryCreateDto, @MappingTarget Category category);

    @Named("categoriesById")
    default Set<Category> categoriesById(Set<Long> ids) {
        return ids.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}
