package com.ecommerce.app.orderitem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatusHistory, Long> {
}
