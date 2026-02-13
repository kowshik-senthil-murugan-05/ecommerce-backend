package com.ecommerce.app.product;

import com.ecommerce.app.category.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "ecomm_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productImage;
    private String productName;
    private String description;
    private double productPrice;
    private double discount;
    private double finalPrice;
    private long sellerId; //user
    private int availableProductQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public String getProductImage() {
        return productImage;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    //    public void setFinalPrice() {
////        this.finalPrice = calculationOfFinalPrice(productPrice, discount);
//    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public int getAvailableProductQuantity() {
        return availableProductQuantity;
    }

    public void setAvailableProductQuantity(int availableProductQuantity) {
        this.availableProductQuantity = availableProductQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
