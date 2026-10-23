package com.ecommercefml.order.controller;

import com.ecommercefml.order.dto.OrderRequestDto;
import com.ecommercefml.order.dto.OrderResponseDto;
import com.ecommercefml.order.service.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }
}
