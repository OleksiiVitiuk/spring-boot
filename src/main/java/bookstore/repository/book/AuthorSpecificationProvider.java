package bookstore.repository.book;

import bookstore.entity.Book;
import bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_KEY = "author";

    @Override
    public String getKey() {
        return AUTHOR_KEY;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(AUTHOR_KEY).in(param);
    }
}
