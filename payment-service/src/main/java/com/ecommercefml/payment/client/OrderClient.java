package com.ecommercefml.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service")
public interface OrderClient {
    @PostMapping("/api/order/{orderId}/confirm")
    void confirmOrder(@PathVariable String orderId);

    @PostMapping("/api/order/{orderId}/fail")
    void failOrder(@PathVariable String orderId);
}
