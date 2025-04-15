package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookDtoWithoutCategoryIds;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.entity.Book;
import bookstore.entity.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(Book book, @MappingTarget BookDto bookDto) {
        bookDto.setCategoryIds(book.getCategories()
                .stream().map(Category::getId)
                .collect(Collectors.toSet())
        );
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoriesById")
    Book toModel(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoriesById")
    void updateModel(CreateBookRequestDto createBookRequestDto, @MappingTarget Book book);
}
