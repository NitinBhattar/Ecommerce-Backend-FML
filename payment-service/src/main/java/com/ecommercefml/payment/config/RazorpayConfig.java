package com.ecommercefml.payment.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {
    private final String key;
    private final String secret;

    // ctor
    public RazorpayConfig(@Value("${razorpay.key}") String key, @Value("${razorpay.secret}") String secret) {
        this.key = key;
        this.secret = secret;
    }

    // Razorpay client
    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient(key, secret);
    }

    
}
