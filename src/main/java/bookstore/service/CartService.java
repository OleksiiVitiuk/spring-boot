package bookstore.service;

import bookstore.dto.cart.CartDto;
import bookstore.dto.cart.CartItemCreateRequestDto;
import bookstore.entity.Cart;
import bookstore.entity.User;
import org.springframework.security.core.Authentication;

public interface CartService {
    CartDto getCart(Long userId, Authentication authentication);

    CartDto addCartItem(Long userId, Authentication authentication,
                        CartItemCreateRequestDto createItemRequestDto);

    CartDto updateCartItem(Long userId, Authentication authentication,
                           Long cartItemId,
                           CartItemCreateRequestDto createItemRequestDto);

    void deleteCart(Long cartItemId, Long itemId);

    Cart createCartForUser(User user);
}
