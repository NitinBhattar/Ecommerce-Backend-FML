package com.ecommercefml.payment.service.impl;

import com.ecommercefml.payment.client.InventoryClient;
import com.ecommercefml.payment.client.OrderClient;
import com.ecommercefml.payment.entity.Payment;
import com.ecommercefml.payment.enums.PaymentStatus;
import com.ecommercefml.payment.exception.PaymentNotFoundException;
import com.ecommercefml.payment.repository.PaymentRepository;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RazorpayWebhookService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;

    @Value("${razorpay.webhook-secret}")
    private String webhookSecret;

    public void processWebhook(String signature, String payload) {
        verifySignature(signature, payload);

        JSONObject event = new JSONObject(payload);
        String eventType = event.getString("event");
        log.info("Received Razorpay Webhook event {}", eventType);

        switch(eventType) {
            case "payment.captured" -> handlePaymentSuccess(event);
            case "payment.failed" -> handlePaymentFailure(event);

            default -> log.warn("Unhandled Razorpay event {}", eventType);
        }
    }

    // Signature verification
    private void verifySignature(String signature, String payload) {
        try {
            Utils.verifyWebhookSignature(payload, signature, webhookSecret);
        } catch (Exception e) {
            log.error("Invalid Razorpay webhook signature");
            throw new SecurityException("Invalid Razorpay webhook signature");
        }
    }

    // Payment success
    private void handlePaymentSuccess(JSONObject event) {
        // Extracting necessary info from event
        JSONObject paymentEntity = extractPaymentEntity(event);

        String razorpayOrderId = paymentEntity.getString("order_id");
        String razorpayPaymentId = paymentEntity.getString("id");

        Payment payment = paymentRepository.findByGatewayOrderId(razorpayOrderId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for gateway order id " + razorpayOrderId)
        );

        // Saving in repo
        payment.setGatewayPaymentId(razorpayPaymentId);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        Payment savedPayment = paymentRepository.save(payment);

        // Calling order and inventory service
        String orderId = savedPayment.getOrderId();

        orderClient.confirmOrder(orderId);
        inventoryClient.commitInventory(orderId);
    }

    // Payment failed
    private void handlePaymentFailure(JSONObject event) {
        // Extracting necessary info from event
        JSONObject paymentEntity = extractPaymentEntity(event);

        String razorpayOrderId = paymentEntity.getString("order_id");
        String razorpayPaymentId = paymentEntity.getString("id");

        Payment payment = paymentRepository.findByGatewayOrderId(razorpayOrderId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for gateway order id " + razorpayOrderId)
        );

        // Saving in repo
        payment.setGatewayPaymentId(razorpayPaymentId);
        payment.setPaymentStatus(PaymentStatus.FAILED);
        Payment savedPayment = paymentRepository.save(payment);

        // Calling order and inventory service
        String orderId = savedPayment.getOrderId();

        orderClient.failOrder(orderId);
        inventoryClient.rollbackInventory(orderId);
    }

    // Extracting info
    private JSONObject extractPaymentEntity(JSONObject event) {
        return event.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");
    }
}
