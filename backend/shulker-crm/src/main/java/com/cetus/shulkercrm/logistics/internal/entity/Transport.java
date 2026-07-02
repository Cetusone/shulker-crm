package com.cetus.shulkercrm.logistics.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "transports",
        indexes = {
                @Index(name = "idx_transports_transport_type", columnList = "transport_type")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false, length = 20)
    private TransportType transportType;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Positive
    @Column(name = "max_weight_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxWeightKg;

    @Positive
    @Column(name = "max_volume_m3", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxVolumeM3;

    @Positive
    @Column(name = "speed_km_h", nullable = false, precision = 8, scale = 2)
    private BigDecimal speedKmH;

    @Positive
    @Column(name = "cost_per_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerKm;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}