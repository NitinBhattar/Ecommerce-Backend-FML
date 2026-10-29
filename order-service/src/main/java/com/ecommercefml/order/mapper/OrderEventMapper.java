package com.ecommercefml.order.mapper;

import com.ecommercefml.order.enity.Order;
import com.ecommercefml.order.event.OrderPlacedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderEventMapper {
    @Mapping(source = "eventId", target = "eventId")
    @Mapping(target = "eventTime", expression = "java(java.time.Instant.now())")
    OrderPlacedEvent toEvent(Order order, String eventId);
}
