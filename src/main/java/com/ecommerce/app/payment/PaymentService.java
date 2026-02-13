package com.ecommerce.app.payment;

public interface PaymentService
{
    PaymentDTO makePayment(PaymentInputDTO dto);

    PaymentDTO getPaymentDetails(long orderId);

    PaymentDTO refundPayment(long paymentId);
}
