package com.ecommercefml.order.service.impl;

import com.ecommercefml.order.client.InventoryFeignClient;
import com.ecommercefml.order.dto.InventoryResponseDto;
import com.ecommercefml.order.dto.OrderRequestDto;
import com.ecommercefml.order.dto.OrderResponseDto;
import com.ecommercefml.order.enity.Order;
import com.ecommercefml.order.enums.OrderStatus;
import com.ecommercefml.order.event.OrderPlacedEvent;
import com.ecommercefml.order.exception.ProductOutOfStockException;
import com.ecommercefml.order.kafka.OrderEventProducer;
import com.ecommercefml.order.mapper.OrderEventMapper;
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
    private final InventoryFeignClient inventoryFeignClient;
    private final OrderEventProducer orderEventProducer;
    private final OrderEventMapper orderEventMapper;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        // Using feign for stock
        InventoryResponseDto inventoryResponseDto = inventoryFeignClient.isInStock(orderRequestDto.getSkuCode());

        if (!inventoryResponseDto.isInStock()) {
            throw new ProductOutOfStockException("Out of stock!");
        }

        // Repository changes
        Order order = orderMapper.toEntity(orderRequestDto);

        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        // Order placed event generation
        String eventId = UUID.randomUUID().toString();
        OrderPlacedEvent orderPlacedEvent = orderEventMapper.toEvent(savedOrder, eventId);
        orderEventProducer.sendOrderEvent(orderPlacedEvent);

        return orderMapper.toDto(savedOrder);
    }
}
