package bookstore.service;

import bookstore.dto.order.OrderCreateRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.OrderStatusPatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    Page<OrderDto> getOrders(Authentication authentication,
                             Pageable pageable);

    OrderDto createOrder(Authentication authentication,
                         OrderCreateRequestDto requestDto);

    OrderDto setNewStatus(Long id,
                          OrderStatusPatchDto statusPatchDto);

    Page<OrderItemDto> getOrderItems(Authentication authentication,
                                     Pageable pageable, Long id);

    OrderItemDto getOrderItem(Authentication authentication,
                              Long orderId,
                              Long itemId);
}
