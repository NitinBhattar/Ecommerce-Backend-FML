package com.ecommercefml.payment.dto;

import com.ecommercefml.payment.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    @NotNull
    private String orderId;
    @Positive
    private BigDecimal amount;
    @NotNull
    private PaymentMethod paymentMethod;
}
