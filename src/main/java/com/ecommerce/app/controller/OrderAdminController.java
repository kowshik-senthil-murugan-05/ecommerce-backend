package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIException;
import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.order.OrderViewDTO;
import com.ecommerce.app.order.OrderService;
import com.ecommerce.app.orderitem.OrderStatusUpdateDTO;
import com.ecommerce.app.util.AppUtil;
import com.ecommerce.app.util.PageDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/order")
//@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {

    private final OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Update order status
    @PostMapping("/status/update")
    public ResponseEntity<APIResponse<OrderViewDTO>> updateOrderStatus(@RequestBody OrderStatusUpdateDTO statusUpdateDTO) {

        System.out.println("order status -" + statusUpdateDTO.orderStatus);

        try {
            OrderViewDTO updatedOrder = orderService.updateOrderStatus(statusUpdateDTO);

            return new ResponseEntity<>(
                    new APIResponse<>("Order status updated!", true, updatedOrder),
                    HttpStatus.OK
            );
        }catch (APIException e)
        {
            return new ResponseEntity<>(
                    new APIResponse<>(
                            "Order status - '" + statusUpdateDTO.orderStatus.toUpperCase() + "' already exists!!",
                            false,
                            null
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // List orders by month
    @GetMapping("/list/{year}/{month}")
    public ResponseEntity<PageDetails<OrderViewDTO>> listOrdersForMonth(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder) {

        PageDetails<OrderViewDTO> orders = orderService.listOrdersForMonth(year, month, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Delete order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<APIResponse<OrderViewDTO>> deleteOrder(@PathVariable long orderId) {
        OrderViewDTO orderDTO = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(
                new APIResponse<>("Order deleted!", true, orderDTO),
                HttpStatus.OK
        );
    }
}
