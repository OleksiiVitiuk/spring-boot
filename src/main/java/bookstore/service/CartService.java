package bookstore.service;

import bookstore.dto.cart.CartDto;
import bookstore.dto.cart.CartItemCreateRequestDto;
import org.springframework.security.core.Authentication;

public interface CartService {
    CartDto getCart(Authentication authentication);

    CartDto addCartItem(Authentication authentication,
                        CartItemCreateRequestDto createItemRequestDto);

    CartDto updateCartItem(Authentication authentication,
                           Long cartItemId,
                           CartItemCreateRequestDto createItemRequestDto);

    void deleteCart(Long cartItemId);
}
