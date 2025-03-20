package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.book.BookDto;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateModel(CreateBookRequestDto createBookRequestDto, @MappingTarget Book book);
}
