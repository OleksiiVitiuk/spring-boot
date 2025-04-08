package bookstore.dto.cart;

import lombok.Data;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItemDtos;
}
