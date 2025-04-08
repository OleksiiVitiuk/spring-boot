package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.order.OrderItemDto;
import bookstore.entity.CartItem;
import bookstore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "price", source = "cartItem.book.price")
    OrderItem toOrderItemFromCartItem(CartItem cartItem);
}
