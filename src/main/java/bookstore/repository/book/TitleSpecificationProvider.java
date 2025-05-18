package bookstore.repository.book;

import bookstore.entity.Book;
import bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String TITLE_KEY = "title";

    @Override
    public String getKey() {
        return TITLE_KEY;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(TITLE_KEY).in(param);
    }
}
