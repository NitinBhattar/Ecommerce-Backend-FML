package com.ecommercefml.order.service.impl;

import com.ecommercefml.order.dto.OrderRequestDto;
import com.ecommercefml.order.dto.OrderResponseDto;
import com.ecommercefml.order.enity.Order;
import com.ecommercefml.order.enums.OrderStatus;
import com.ecommercefml.order.mapper.OrderMapper;
import com.ecommercefml.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.toEntity(orderRequestDto);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }
}
