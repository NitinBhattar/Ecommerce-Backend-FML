package com.ecommercefml.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "processed_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderId;

    private Instant processedAt;

    @PrePersist
    void onCreate() {
        processedAt = Instant.now();
    }
}
