package com.ecommerce.app.order;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderRequestDTO
{
    public long addressId;

//    @NotEmpty(message = "Order must contain atleast one item!!")
//    public List<OrderItemRequestDTO> orderItemRequestDTOS;

    public long productId;
    public int productQuantity;

    public String paymentType;

    @Override
    public String toString() {
        return "OrderRequestDTO{" +
                "addressId=" + addressId +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
