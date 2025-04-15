package bookstore.controller;

import bookstore.dto.order.OrderCreateRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.order.OrderItemDto;
import bookstore.dto.order.OrderStatusPatchDto;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "user can make order and get it, admin can change status order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get all user`s orders",
            description = "Returns a paginated list of orders")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<OrderDto> getOrders(Authentication authentication, Pageable pageable) {
        return orderService.getOrders(authentication, pageable);
    }

    @Operation(
            summary = "Get all items from a specific order",
            description = "Returns a paginated list of items from a specific "
                    + "order belonging to the authenticated user."
    )
    @GetMapping("{id}/items")
    @PreAuthorize("hasRole('USER')")
    public Page<OrderItemDto> getOrderItems(Authentication authentication,
                                            Pageable pageable,
                                            @PathVariable Long id) {
        return orderService.getOrderItems(authentication, pageable, id);
    }

    @Operation(summary = "Get a specific item from an order",
            description = "Returns details about a specific item (order item ID) within "
                    + "a specific order (order ID) for the authenticated user.")
    @GetMapping("{id}/items/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public OrderItemDto getOrderItem(Authentication authentication,
                                     @PathVariable Long id,
                                     @PathVariable Long orderId) {
        return orderService.getOrderItem(authentication, id, orderId);
    }

    @Operation(summary = "Create a new order",
            description = "Creates a new order for the authenticated "
                    + "user using the items in their cart.")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(Authentication authentication,
                                @RequestBody @Valid OrderCreateRequestDto requestDto) {
        return orderService.createOrder(authentication, requestDto);
    }

    @Operation(summary = "Update order status",
            description = "Allows an admin to update the status of an order using its ID.")
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto setStatus(@PathVariable Long id,
                              @RequestBody @Valid OrderStatusPatchDto statusPatchDto) {
        return orderService.setNewStatus(id, statusPatchDto);
    }
}
