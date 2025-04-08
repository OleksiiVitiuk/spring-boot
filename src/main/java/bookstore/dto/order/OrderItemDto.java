package bookstore.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long bookId;
    private int quantity;
    private BigDecimal price;
}
