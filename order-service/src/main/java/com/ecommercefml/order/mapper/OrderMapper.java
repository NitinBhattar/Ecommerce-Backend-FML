package com.ecommercefml.order.mapper;

import com.ecommercefml.order.dto.OrderRequestDto;
import com.ecommercefml.order.dto.OrderResponseDto;
import com.ecommercefml.order.enity.Order;
import com.ecommercefml.order.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    public Order toEntity(OrderRequestDto orderRequestDto);

    public OrderResponseDto toDto(Order order);
}
