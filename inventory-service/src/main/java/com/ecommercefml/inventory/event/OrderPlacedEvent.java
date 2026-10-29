package com.ecommercefml.inventory.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
public class OrderPlacedEvent {
    private String eventId;
    private String orderId;
    private String skuCode;
    private Integer quantity;
    private Instant eventTime;
}

