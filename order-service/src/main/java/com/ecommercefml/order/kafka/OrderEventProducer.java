package com.ecommercefml.order.kafka;

import com.ecommercefml.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void sendOrderEvent(OrderPlacedEvent orderPlacedEvent) {
        kafkaTemplate.send("order-event", orderPlacedEvent.getOrderId(), orderPlacedEvent);
    }
}
