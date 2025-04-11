package bookstore.repository;

import bookstore.entity.CartItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCartUserId(Long cartItemId, Long userId);

    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);

    Set<CartItem> findByCartUserId(Long id);
}
