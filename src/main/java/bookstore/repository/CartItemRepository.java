package bookstore.repository;

import bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCartUserId(Long cartItemId, Long userId);

    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);

    Set<CartItem> findByCartUserId(Long id);
}
