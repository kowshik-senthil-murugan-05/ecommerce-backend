package com.ecommerce.app.order;

import com.ecommerce.app.orderitem.OrderStatusHistory;
import com.ecommerce.app.payment.Payment;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ecomm_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long buyerId;
    private LocalDateTime orderDate;
    private double totalAmount;

//    @Enumerated(EnumType.STRING)
//    private OrderStatus status;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> items;

    private long productId;
    private String productName;
    private int quantity;
    private double productPrice;
    private long sellerId;
    private OrderStatus curStatus;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;//todo check

    private long addressId;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();

    public enum OrderStatus {
        ORDER_PLACED,
        SHIPPED,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED;

        public static OrderStatus from(String value)
        {
            return Arrays.stream(values())
                    .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order status!!"));
        }
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

//    public OrderStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(OrderStatus status) {
//        this.status = status;
//    }

//    public List<OrderItem> getItems() {
//        return items;
//    }
//
//    public void setOrderItems(List<OrderItem> items) {
//        this.items = items;
//    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public List<OrderStatusHistory> getOrderStatusHistories() {
        return orderStatusHistories;
    }

    public void setOrderStatusHistories(List<OrderStatusHistory> orderStatusHistories) {
        this.orderStatusHistories = orderStatusHistories;
    }

    public OrderStatus getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(OrderStatus curStatus) {
        this.curStatus = curStatus;
    }
}


