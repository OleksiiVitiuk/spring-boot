package bookstore.dto.order;

import bookstore.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusPatchDto {
    @NotNull
    private Order.Status status;
}
