package com.ecommerce.app.cart;

import com.ecommerce.app.util.PageDetails;

public interface CartService
{
    UserCartDTO addProductToCart(long userId, AddToCartRequestDTO dto);

    PageDetails<UserCartDTO> getAllCarts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    UserCartDTO fetchUserCart(long userId);

    UserCartDTO reduceUserCartItemQuantity(long userCartId, long productId);

    void removeCartItem(long userCartId, long productId);
}
