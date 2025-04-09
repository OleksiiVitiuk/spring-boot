package bookstore.repository;

import bookstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems")
    @Query("SELECT o FROM Order o "
            + "LEFT JOIN FETCH o.orderItems "
            + "WHERE o.user.id = :userId")
    Page<Order> findByUserId(Long userId, Pageable pageable);
}
