package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.order.OrderViewDTO;
import com.ecommerce.app.order.OrderService;
import com.ecommerce.app.orderitem.OrderStatusUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/orders")
@PreAuthorize("hasRole('SELLER')")
public class OrderSellerController {

    private final OrderService orderService;

    public OrderSellerController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Fetch all order items for a seller
//    @GetMapping("/list/{sellerId}")
//    public ResponseEntity<PageDetails<OrderItemDTO>> fetchOrdersForSeller(
//            @PathVariable long sellerId,
//            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
//            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
//            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder) {
//
//        PageDetails<OrderItemDTO> pageDetails =
//                orderService.fetchOrdersForSeller(sellerId, pageNumber, pageSize, sortBy, sortOrder);
//
//        return new ResponseEntity<>(pageDetails, HttpStatus.OK);
//    }

    // Seller can update item status (ex: mark as shipped)
    @PutMapping("/update-status")
    public ResponseEntity<APIResponse<OrderViewDTO>> updateOrderItemStatus(
            @Valid @RequestBody OrderStatusUpdateDTO orderStatusUpdateDTO) {

        OrderViewDTO updatedItem = orderService.updateOrderStatus(orderStatusUpdateDTO);

        return new ResponseEntity<>(
                new APIResponse<>("Order item status updated!", true, updatedItem),
                HttpStatus.OK
        );
    }

}

