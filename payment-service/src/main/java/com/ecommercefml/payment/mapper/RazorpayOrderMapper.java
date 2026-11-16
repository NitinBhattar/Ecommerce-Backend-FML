package com.ecommercefml.payment.mapper;

import com.ecommercefml.payment.dto.GatewayOrderResponseDto;
import com.ecommercefml.payment.enums.PaymentGateway;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RazorpayOrderMapper {
    // Options
    public JSONObject toOptions(String orderId, String transactionId, BigDecimal amount) {
        JSONObject options = new JSONObject();

        // Basics
        options.put("amount", amount.multiply(BigDecimal.valueOf(100)));
        options.put("currency", "INR");
        options.put("receipt", transactionId);

        // Extras
        JSONObject notes = new JSONObject();
        notes.put("orderId", orderId);
        notes.put("transactionId", transactionId);

        options.put("notes", notes);

        return options;
    }

    // Order -> response
    public GatewayOrderResponseDto toDto(Order order, PaymentGateway paymentGateway) {
        return new GatewayOrderResponseDto(
                order.get("id"),
                order.get("currency"),
                order.get("status"),
                paymentGateway
        );
    }
}
