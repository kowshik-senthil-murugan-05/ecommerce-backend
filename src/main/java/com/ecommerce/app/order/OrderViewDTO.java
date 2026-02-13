package com.ecommerce.app.order;

import com.ecommerce.app.appuser.address.UserAddressDTO;
import com.ecommerce.app.orderitem.OrderStatusHistoryDTO;
import com.ecommerce.app.payment.PaymentDTO;

import java.util.List;

//One order one item
public class OrderViewDTO {

    public long orderId;
    public String userName;
//    public List<OrderItemDTO> items;
    public long productId;
    public String productName;
    public int quantity;
    public double productPrice;
    public String sellerName;
    public double totalAmount;
    public String orderDate;
    public PaymentDTO paymentDTO;
    public String currentOrderStatus;
    public List<OrderStatusHistoryDTO> orderStatusHistoryDTOS;
    public UserAddressDTO address;



}
