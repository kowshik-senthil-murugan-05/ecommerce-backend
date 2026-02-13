package com.ecommerce.app.cart;

import com.ecommerce.app.cartitem.CartItemDTO;

import java.util.List;

public class UserCartDTO
{
    public long cartId;
    public long userId;
    public String userName;
    public List<CartItemDTO> cartItemDTOS;
    public double totalPrice;
}
