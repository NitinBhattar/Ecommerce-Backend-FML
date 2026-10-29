package com.ecommercefml.inventory.consumer;

import com.ecommercefml.inventory.event.OrderPlacedEvent;
import com.ecommercefml.inventory.service.impl.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-event", groupId = "inventory-group")
    public void consumeOrderEvent(OrderPlacedEvent orderPlacedEvent) {
        inventoryService.updateInventory(orderPlacedEvent);
    }
}
