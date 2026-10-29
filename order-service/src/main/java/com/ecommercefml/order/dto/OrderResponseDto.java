package com.ecommercefml.order.dto;

import com.ecommercefml.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private String orderId;
    private OrderStatus orderStatus;
}
