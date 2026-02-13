package com.ecommerce.app.controller;

import com.ecommerce.app.cart.AddToCartRequestDTO;
import com.ecommerce.app.cart.CartService;
import com.ecommerce.app.cart.UserCartDTO;
import com.ecommerce.app.cartitem.CartItemRemoveReqDTO;
import com.ecommerce.app.exceptionhandler.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController
{

    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @PostMapping("/product/addToCart/{userId}")
    public ResponseEntity<APIResponse<UserCartDTO>> addProductToCart(
            @PathVariable long userId, @RequestBody AddToCartRequestDTO requestDTO)
    {
        /*
         *   Adding products to cart is tested. It is working fine.
         *   todo - Adding same product again results in creation of two rows, it should be added in same row.
         */
        System.out.println("user id -> " + userId);
        System.out.println("dto -> " + requestDTO);
        UserCartDTO dto = cartService.addProductToCart(userId, requestDTO);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Product added to cart!!",
                        true,
                        dto),
                HttpStatus.CREATED
        );
    }//

    @GetMapping("/fetch/{userId}")
    public ResponseEntity<UserCartDTO> fetchCartForUser(@PathVariable long userId){

        System.out.println("user id -> " + userId);

        UserCartDTO userCart = cartService.fetchUserCart(userId);

        return new ResponseEntity<>(
                userCart,
                HttpStatus.OK
        );
    }

    @PostMapping("/cartItem/remove")
    public ResponseEntity<APIResponse<Void>> removeCartItem(@RequestBody CartItemRemoveReqDTO cartItemRemoveReqDTO)
    {
        System.out.println("cart item -> " + cartItemRemoveReqDTO);

        cartService.removeCartItem(cartItemRemoveReqDTO.userCartId, cartItemRemoveReqDTO.productId);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Cart item removed successfully!!",
                        true, null),
                HttpStatus.OK
        );
    }

    @PostMapping("/cartItem/quantity/reduce/{userCartId}/{productId}")
    public ResponseEntity<UserCartDTO> reduceCartItemQuantity(@PathVariable long userCartId, @PathVariable long productId)
    {
        UserCartDTO reducedUserCartItemQuantity = cartService.reduceUserCartItemQuantity(userCartId, productId);

        return new ResponseEntity<UserCartDTO>(
                reducedUserCartItemQuantity,
                HttpStatus.OK
        );
    }
}

