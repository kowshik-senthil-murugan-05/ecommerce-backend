package com.ecommerce.app.order;

import com.ecommerce.app.orderitem.OrderStatusUpdateDTO;
import com.ecommerce.app.util.PageDetails;

public interface OrderService
{
    OrderViewDTO placeOrder(OrderRequestDTO orderRequestDTO, long userId);

    PageDetails<OrderViewDTO> fetchOrdersForUser(long userId, int pageNum, int pageSize, String sortBy, String sortOrder);

    public OrderViewDTO buildOrderDetail(long orderId);

    OrderViewDTO updateOrderStatus(OrderStatusUpdateDTO statusUpdateDTO);

    Order getOrderObj(long orderId);

    Order saveOrderObj(Order order);

    OrderViewDTO getOrderData(long orderId);

    OrderViewDTO deleteOrder(long orderId);

    PageDetails<OrderViewDTO> listOrdersForMonth(int year, int month, int pageNum, int pageSize, String sortBy, String sortOrder);

//    PageDetails<OrderItemDTO> fetchOrdersForSeller(long sellerId, int pageNum, int pageSize, String sortBy, String sortOrder);
}
