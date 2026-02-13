package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.payment.PaymentDTO;
import com.ecommerce.app.payment.PaymentInputDTO;
import com.ecommerce.app.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentRestController
{
    private final PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @PostMapping("/make")
    public ResponseEntity<APIResponse<PaymentDTO>> makePayment(@RequestBody PaymentInputDTO dto) {
        PaymentDTO paymentDTO = paymentService.makePayment(dto);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Payment done!!",
                        true,
                        paymentDTO
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/forOrder/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentForOrder(@PathVariable long orderId) {
        PaymentDTO paymentForOrder = paymentService.getPaymentDetails(orderId);

        return new ResponseEntity<>(
                paymentForOrder,
                HttpStatus.OK
        );
    }


}
