package com.ecommerce.app.cartitem;

import com.ecommerce.app.cart.UserCart;
import com.ecommerce.app.util.AppUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Table
@Entity(name = CartItem.TABLE_NAME)
public class CartItem
{
    public static final String TABLE_NAME = AppUtil.TABLE_NAME_PREFIX + "user_cart_item";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private String productName;
    private int productQuantity;
    private String productImage;
    private String description;
    private double productPrice;
    private double curCartItemPrice;
    private long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private UserCart userCart;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double getCurCartItemPrice() {
        return curCartItemPrice;
    }

    public void setCurCartItemPrice(double curCartItemPrice) {
        this.curCartItemPrice = curCartItemPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public UserCart getUserCart() {
        return userCart;
    }

    public void setUserCart(UserCart userCart) {
        this.userCart = userCart;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

//    @Override
//    public String toString() {
//        return "CartItem{" +
//                "id=" + id +
//                ", productId=" + productId +
//                ", productName='" + productName + '\'' +
//                ", productQuantity=" + productQuantity +
//                ", productImage='" + productImage + '\'' +
//                ", description='" + description + '\'' +
//                ", productPrice=" + productPrice +
//                ", curCartItemPrice=" + curCartItemPrice +
//                '}';
//    }
}
