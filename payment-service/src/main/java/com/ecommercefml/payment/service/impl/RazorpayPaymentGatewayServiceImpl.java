package com.ecommercefml.payment.service.impl;

import com.ecommercefml.payment.dto.GatewayOrderResponseDto;

import com.ecommercefml.payment.enums.PaymentGateway;
import com.ecommercefml.payment.mapper.RazorpayOrderMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayPaymentGatewayServiceImpl implements PaymentGatewayService {
    private final RazorpayClient razorpayClient;
    private final RazorpayOrderMapper razorpayOrderMapper;

    @Override
    public GatewayOrderResponseDto createOrder(String orderId, String transactionId, BigDecimal amount) {
        try {
            // Options mapper
            JSONObject options = razorpayOrderMapper.toOptions(orderId, transactionId, amount);

            // Creating order
            Order order = razorpayClient.orders.create(options);

            // Response mapper
            return razorpayOrderMapper.toDto(order, PaymentGateway.RAZORPAY);
        } catch (Exception e) {
            throw new IllegalStateException("Razorpay order creation failed ", e);
        }
    }
}
