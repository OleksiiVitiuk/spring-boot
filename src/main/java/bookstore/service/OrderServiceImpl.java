package bookstore.service;

import bookstore.dto.order.OrderCreateRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.OrderStatusPatchDto;
import bookstore.entity.CartItem;
import bookstore.entity.Order;
import bookstore.entity.OrderItem;
import bookstore.entity.User;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.repository.CartItemRepository;
import bookstore.repository.OrderItemRepository;
import bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Page<OrderDto> getOrders(Authentication authentication,
                                    Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderDto createOrder(Authentication authentication,
                                OrderCreateRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();

        Order newOrder = orderMapper.toModelForCreating(requestDto);
        newOrder.setUser(user);
        newOrder.setStatus(Order.Status.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());

        Set<CartItem> cartItems = cartItemRepository.findByCartUserId(user.getId());

        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newOrder.setTotal(total);

        newOrder = orderRepository.saveAndFlush(newOrder);

        Order finalNewOrder = newOrder;
        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(finalNewOrder);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    return orderItem;
                })
                .collect(Collectors.toSet());

        newOrder.getOrderItems().addAll(orderItems);

        return orderMapper.toDto(orderRepository.saveAndFlush(newOrder));
    }

    @Override
    public OrderDto setNewStatus(Long id, OrderStatusPatchDto statusPatchDto) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No order with id: " + id));

        order.setStatus(statusPatchDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderItemDto> getOrderItems(Authentication authentication,
                                            Pageable pageable,
                                            Long id) {
        User user = (User) authentication.getPrincipal();
        return orderItemRepository
                .findByOrderIdAndOrderUserId(pageable, id, user.getId())
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemDto getOrderItem(Authentication authentication,
                                     Long orderId,
                                     Long itemId) {
        User user = (User) authentication.getPrincipal();
        return orderItemMapper.toDto(
                orderItemRepository
                        .findByIdAndOrderIdAndOrderUserId(itemId,
                                orderId,
                                user.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Order with id: " + orderId
                                        + " has no item with id: " + itemId)));
    }

}
