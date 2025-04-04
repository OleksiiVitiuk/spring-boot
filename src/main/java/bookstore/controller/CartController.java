package bookstore.controller;

import bookstore.dto.cart.CartDto;
import bookstore.dto.cart.CartItemCreateRequestDto;
import bookstore.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart controller", description = "Users can add items and remove it")
public class CartController {
    private CartService cartService;

    @Operation(summary = "Get cart",
            description = "Returns authorized user`s cart with list of items")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public CartDto getCarts(Authentication authentication) {
        return cartService.getCart(authentication);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add item to cart",
            description = "Adds a new item to the cart of the authenticated user."
    )
    public CartDto addCartItem(Authentication authentication,
                               @RequestBody @Valid CartItemCreateRequestDto createItemRequestDto) {
        return cartService.addCartItem(authentication, createItemRequestDto);
    }

    @PutMapping("/item/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update cart item",
            description = "Updates the quantity or details of an existing cart item."
    )
    public CartDto updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemCreateRequestDto createItemRequestDto
    ) {
        return cartService.updateCartItem(authentication, cartItemId, createItemRequestDto);
    }

    @DeleteMapping("/item/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete cart item",
            description = "Removes an item from the cart by its ID."
    )
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCart(cartItemId);
    }
}
