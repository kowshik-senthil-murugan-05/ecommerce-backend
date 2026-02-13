package com.ecommerce.app.orderitem;

import com.ecommerce.app.order.Order;
import com.ecommerce.app.order.Order.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ecomm_order_status_history")
public class OrderStatusHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime statusDateTime;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderStatusHistory()
    {}

    public OrderStatusHistory(OrderStatus orderStatus, LocalDateTime curTime, Order order)
    {
        this.orderStatus = orderStatus;
        this.statusDateTime = curTime;
        this.order = order;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(LocalDateTime statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
