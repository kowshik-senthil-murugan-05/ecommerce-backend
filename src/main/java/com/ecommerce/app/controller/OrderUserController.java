package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.order.OrderViewDTO;
import com.ecommerce.app.order.OrderRequestDTO;
import com.ecommerce.app.order.OrderService;
import com.ecommerce.app.util.AppUtil;
import com.ecommerce.app.util.PageDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/order")
//@PreAuthorize("hasRole('USER', 'ADMIN')")
public class OrderUserController {

    private final OrderService orderService;

    public OrderUserController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Place order
    @PostMapping("/place/{userId}")
    public ResponseEntity<APIResponse<OrderViewDTO>> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable long userId)
    {
        System.out.println("order req -> " + orderRequestDTO);
        OrderViewDTO placedOrder = orderService.placeOrder(orderRequestDTO, userId);
        return new ResponseEntity<>(
                new APIResponse<>("Order placed successfully!", true, placedOrder),
                HttpStatus.CREATED
        );
    }

    //Get all orders of logged-in user
    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<PageDetails<OrderViewDTO>> getMyOrders(
            @PathVariable long userId,
            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder) {

        System.out.println("fetch orders!!!");
        PageDetails<OrderViewDTO> myOrders = orderService.fetchOrdersForUser(userId, pageNumber, pageSize, sortBy, sortOrder);

        System.out.println("disp orders -> " + myOrders.list);
        return new ResponseEntity<>(myOrders, HttpStatus.OK);
    }

    @GetMapping("/fetch/{orderId}")
    public ResponseEntity<APIResponse<OrderViewDTO>> fetchOrder(@PathVariable long orderId)
    {
        OrderViewDTO orderDetail = orderService.buildOrderDetail(orderId);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Fetched order detail successfully!!",
                        true,
                        orderDetail
                ),
                HttpStatus.OK
        );
    }
}

