package com.ecommercefml.payment.dto;

import com.ecommercefml.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private String orderId;
    private String transactionId;
    private String gatewayOrderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
}
