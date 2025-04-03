package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.cart.CartItemCreateRequestDto;
import bookstore.dto.cart.CartItemDto;
import bookstore.entity.Book;
import bookstore.entity.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface CartItemsMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toModel(CartItemCreateRequestDto createItemRequestDto);

    @AfterMapping
    default void implBook(@MappingTarget CartItem cartItem,
                          CartItemCreateRequestDto createRequestDto) {
        cartItem.setBook(new Book(createRequestDto.getBookId()));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "book", ignore = true)
    void updateCartItem(@MappingTarget CartItem cartItem,
                        CartItemCreateRequestDto cartItemCreateRequestDto);

    @Named("cartItemsToDto")
    default Set<CartItemDto> cartItemsToDto(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(i -> new CartItemDto(
                        i.getId(),
                        i.getBook().getId(),
                        i.getBook().getTitle(),
                        i.getQuantity()))
                .collect(Collectors.toSet());
    }

}
