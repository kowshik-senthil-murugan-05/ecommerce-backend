package com.ecommerce.app.order;

public class OrderItemRequestDTO
{
    public long productId;
    public int productQuantity;

    @Override
    public String toString() {
        return "OrderItemRequestDTO{" +
                "productId=" + productId +
                ", quantity=" + productQuantity +
                '}';
    }
}
