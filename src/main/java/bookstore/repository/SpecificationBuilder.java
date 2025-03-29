package bookstore.repository;

import bookstore.dto.book.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification build(BookSearchParametersDto searchParametersDto);
}
