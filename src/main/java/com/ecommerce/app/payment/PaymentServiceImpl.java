package com.ecommerce.app.payment;

import com.ecommerce.app.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.app.order.OrderViewDTO;
import com.ecommerce.app.order.OrderService;
import com.ecommerce.app.payment.Payment.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService
{
    private final PaymentRepo paymentRepo;
    private final OrderService orderService;

    public PaymentServiceImpl(PaymentRepo paymentRepo, OrderService orderService) {
        this.paymentRepo = paymentRepo;
        this.orderService = orderService;
    }


//    @Transactional
    @Override
    public PaymentDTO makePayment(PaymentInputDTO dto)
    {
        OrderViewDTO order = orderService.getOrderData(dto.orderId);

//        if (order == null || order.userId != dto.userId) {
//            throw new RuntimeException("Invalid order or user");
//        }

        if (order.totalAmount != dto.amount) {
            throw new RuntimeException("Payment amount does not match order total");
        }

        Payment payment = new Payment();

        payment.setOrderId(dto.orderId);
        payment.setAmount(dto.amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.COMPLETED);

//        order.setStatus(Order.OrderStatus.PAID);
//        orderService.saveOrderObj(order);
        return convertEntityToDTO(paymentRepo.save(payment));
    }

    private PaymentDTO convertEntityToDTO(Payment payment)
    {
        PaymentDTO dto = new PaymentDTO();
        dto.paymentId = payment.getId();
        dto.orderId = payment.getOrderId();
        dto.amount = payment.getAmount();
        dto.paymentDate = payment.getPaymentDate().toString();
        dto.paymentStatus = payment.getStatus().name();

        return dto;
    }

    public PaymentDTO getPaymentDetails(long orderId) {
        Payment payment = paymentRepo.findTopByOrderIdOrderByPaymentDateDesc(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order id", orderId));

        return convertEntityToDTO(payment);
    }

    @Override
    public PaymentDTO refundPayment(long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.REFUNDED);
        return convertEntityToDTO(paymentRepo.save(payment));
    }

}
