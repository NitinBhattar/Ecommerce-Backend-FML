package com.ecommercefml.payment.controller;

import com.ecommercefml.payment.dto.PaymentRequestDto;
import com.ecommercefml.payment.dto.PaymentResponseDto;
import com.ecommercefml.payment.dto.PaymentStatusUpdateRequestDto;
import com.ecommercefml.payment.service.impl.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(paymentRequestDto));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(@RequestParam String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponseDto> updatePaymentStatus(@RequestParam Long paymentId, @Valid @RequestBody PaymentStatusUpdateRequestDto paymentStatusUpdateRequestDto) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, paymentStatusUpdateRequestDto));
    }
}
