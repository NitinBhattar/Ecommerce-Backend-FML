package com.ecommercefml.order.service.impl;

import com.ecommercefml.order.dto.OrderRequestDto;
import com.ecommercefml.order.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);
}
