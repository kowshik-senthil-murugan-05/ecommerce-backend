package com.ecommerce.app.cart;

public class AddToCartRequestDTO
{
    public long productId;
    public int productQuantity;

    @Override
    public String toString() {
        return "AddToCartRequestDTO{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
