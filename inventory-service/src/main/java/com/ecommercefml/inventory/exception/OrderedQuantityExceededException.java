package com.ecommercefml.inventory.exception;

public class OrderedQuantityExceededException extends RuntimeException {
    public OrderedQuantityExceededException(String skuCode) {
        super("Insufficient inventory for SKU: " + skuCode);
    }
}
