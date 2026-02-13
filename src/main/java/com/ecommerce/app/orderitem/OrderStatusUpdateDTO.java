package com.ecommerce.app.orderitem;

import jakarta.validation.constraints.NotBlank;

public class OrderStatusUpdateDTO
{
    public long orderId;
    @NotBlank
    public String orderStatus;
}
