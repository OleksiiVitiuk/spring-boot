package bookstore.repository;

import bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCartUserId(Long cartItemId, Long userId);

    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);
}
