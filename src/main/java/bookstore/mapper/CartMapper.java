package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.cart.CartDto;
import bookstore.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class,
        uses = {BookMapper.class, CartItemsMapper.class})
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItemDtos",
            source = "cart.cartItems",
            qualifiedByName = "cartItemsToDto")
    CartDto toDto(Cart cart);

    @Mapping(target = "user", ignore = true)
    Cart toModel(CartDto cartDto);

}
