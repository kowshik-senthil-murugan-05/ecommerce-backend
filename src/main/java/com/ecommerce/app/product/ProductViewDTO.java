package com.ecommerce.app.product;

public class ProductViewDTO
{
    public long id;
    public String productName;
    public String description;
    public String category;
    public double productPrice;
    public String imgUrl;
    public double discount;
    public String seller;

    @Override
    public String toString() {
        return "ProductViewDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", productPrice=" + productPrice +
                ", imgUrl='" + imgUrl + '\'' +
                ", discount=" + discount +
                ", seller='" + seller + '\'' +
                '}';
    }
}
