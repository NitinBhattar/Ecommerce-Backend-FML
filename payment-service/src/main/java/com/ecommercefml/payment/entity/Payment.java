package com.ecommercefml.payment.entity;

import com.ecommercefml.payment.enums.PaymentMethod;
import com.ecommercefml.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments", indexes = {
        @Index(columnList = "orderId"),
        @Index(columnList = "transactionId"),
        @Index(columnList = "gatewayOrderId")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderId;

    // Precision of decimal digits and till how many is shown is scale
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(unique = true)
    private String gatewayOrderId;

    @Column(unique = true)
    private String gatewayPaymentId;
    
    private Instant createdAt;
    private Instant updatedAt;

    // Before creating an object, it changes value
    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }
    // Before updating an object, it changes value
    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }
}
