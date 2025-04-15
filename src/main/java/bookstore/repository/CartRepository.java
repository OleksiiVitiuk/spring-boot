package bookstore.repository;

import bookstore.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.book"})
    Optional<Cart> findByUserId(Long id);
}
