package bookstore.repository.book;

import bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    Page<Book> findAll(Specification<Book> bookSpecification,
                       Pageable pageable);

    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);
}
