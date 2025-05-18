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
import bookstore.repository.CartItemRepository;
import bookstore.repository.CartRepository;
import bookstore.repository.book.BookRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CartDto getCart(Long userId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartMapper.toDto(
                cartRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No cart with user id: " + user.getId()
                        ))
        );
    }

    @Override
    public CartDto addCartItem(Long userId, Authentication authentication,
                               CartItemCreateRequestDto createItemRequestDto) {
        User user = (User) authentication.getPrincipal();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No cart found for user with id: " + user.getId())
                );

        Book book = bookRepository.findById(createItemRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with id: " + createItemRequestDto.getBookId())
                );

        CartItem cartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId())
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setBook(book);
                    newCartItem.setCart(cart);
                    return newCartItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + createItemRequestDto.getQuantity());
        cart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto updateCartItem(Long userId, Authentication authentication, Long cartItemId,
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
    public void deleteCart(Long cartItemId, Long itemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}
