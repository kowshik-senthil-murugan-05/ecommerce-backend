package com.ecommerce.app.cartitem;

public class CartItemRemoveReqDTO
{
    public long userCartId;
    public long productId;

    @Override
    public String toString() {
        return "CartItemRemoveReqDTO{" +
                "userCartId=" + userCartId +
                ", productId=" + productId +
                '}';
    }
}
