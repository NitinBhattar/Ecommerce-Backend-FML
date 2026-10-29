package com.ecommercefml.inventory.exception;

public class OrderAlreadyProcessedException extends RuntimeException {
    public OrderAlreadyProcessedException(String orderId) {
        super("Order has already been processed for id: " + orderId);
    }
}
