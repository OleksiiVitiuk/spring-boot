package bookstore.service;

import bookstore.dto.cart.CartDto;
import bookstore.dto.cart.CartItemCreateRequestDto;
import bookstore.entity.Book;
import bookstore.entity.Cart;
import bookstore.entity.CartItem;
import bookstore.entity.User;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemsMapper;
import bookstore.mapper.CartMapper;
import bookstore.repository.BookRepository;
import bookstore.repository.CartItemRepository;
import bookstore.repository.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private CartItemRepository cartItemRepository;
    private CartItemsMapper cartItemMapper;
    private BookRepository bookRepository;
    private EntityManager entityManager;

    @Override
    public CartDto getCarts(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartMapper.toDto(
                cartRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No cart with user id: " + user.getId()
                        ))
        );
    }

    @Override
    public CartDto addCartItem(Authentication authentication,
                               CartItemCreateRequestDto createItemRequestDto) {
        User user = (User) authentication.getPrincipal();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(entityManager.merge(user));
                    return cartRepository.save(newCart);
                });

        Book book = bookRepository.findById(createItemRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with id: " + createItemRequestDto.getBookId())
                );

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + createItemRequestDto.getQuantity());
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setQuantity(createItemRequestDto.getQuantity());
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto updateCartItem(Authentication authentication, Long cartItemId,
                                  CartItemCreateRequestDto createItemRequestDto) {
        User user = (User) authentication.getPrincipal();

        CartItem cartItem = cartItemRepository
                .findByIdAndCartUserId(cartItemId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No cart item with id: " + cartItemId));

        cartItemMapper.updateCartItem(cartItem, createItemRequestDto);
        cartItemRepository.save(cartItem);

        return cartMapper.toDto(cartItem.getCart());
    }

    @Override
    public void deleteCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
