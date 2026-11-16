package com.ecommercefml.payment.dto;

import com.ecommercefml.payment.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusUpdateRequestDto {
    @NotNull
    PaymentStatus paymentStatus;
}
