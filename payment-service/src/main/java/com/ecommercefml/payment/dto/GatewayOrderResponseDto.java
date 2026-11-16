package com.ecommercefml.payment.dto;

import com.ecommercefml.payment.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayOrderResponseDto {
    private String gatewayOrderId;
    private String currency;
    private String gatewayOrderStatus;
    private PaymentGateway gatewayName;
}
