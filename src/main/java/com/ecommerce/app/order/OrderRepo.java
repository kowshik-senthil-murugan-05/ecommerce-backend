package com.ecommerce.app.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>
{
    Page<Order> findAllByBuyerId(long userId, Pageable pageable);

    Page<Order> findByOrderDateBetween(OffsetDateTime start, OffsetDateTime end, Pageable pageable);

}
