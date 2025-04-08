package bookstore.repository;

import bookstore.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findByOrderIdAndOrderUserId(Pageable pageable,
                                                Long id,
                                                Long userId);

    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(Long id,
                                                         Long orderId,
                                                         Long userId);
}
