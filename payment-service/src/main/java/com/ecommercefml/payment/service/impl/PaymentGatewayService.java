package com.ecommercefml.payment.service.impl;

import com.ecommercefml.payment.dto.GatewayOrderResponseDto;

import java.math.BigDecimal;

public interface PaymentGatewayService {
    GatewayOrderResponseDto createOrder(String orderId, String transactionId, BigDecimal amount);
}
