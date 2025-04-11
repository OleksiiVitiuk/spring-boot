package bookstore.dto.order;

import bookstore.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItemDtos = new HashSet<>();
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}
