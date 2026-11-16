package com.ecommercefml.payment.controller;

import com.ecommercefml.payment.service.impl.RazorpayWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This webhook is called automatically by razorpay
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class WebhookController {
    private final RazorpayWebhookService razorpayWebhookService;

    @PostMapping("/webhook/razorpay")
    public ResponseEntity<Void> handleWebhook(@RequestHeader("X-Razorpay-Signature") String signature, @RequestBody String payload) {
        razorpayWebhookService.processWebhook(signature, payload);

        return ResponseEntity.ok().build();
    }
}
