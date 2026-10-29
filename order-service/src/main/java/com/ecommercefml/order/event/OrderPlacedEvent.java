package com.ecommercefml.order.event;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderPlacedEvent {
    private String eventId;
    private String orderId;
    private String skuCode;
    private Integer quantity;
    private Instant eventTime;
}
