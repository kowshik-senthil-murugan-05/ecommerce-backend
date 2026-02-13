package com.ecommerce.app.orderitem;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OrderStatusHistoryDTO
{
    public long id;
    public long orderId;
    public String orderStatus;

    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDateTime statusTime;
}
