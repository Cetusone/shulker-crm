package com.cetus.shulkercrm.service.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "stock_movements",
        indexes = {
                @Index(name = "idx_stock_movements_stock_id", columnList = "stock_id"),
                @Index(name = "idx_stock_movements_created_at", columnList = "created_at"),
                @Index(name = "idx_stock_movements_type", columnList = "movement_type")
        }
)
@SQLDelete(sql = "UPDATE stock_movements SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false, updatable = false)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 20, updatable = false)
    private MovementType movementType;

    @Column(name = "quantity_change", nullable = false, updatable = false)
    private Integer quantityChange;

    @Column(name = "quantity_after", nullable = false, updatable = false)
    private Integer quantityAfter;

    @Column(name = "reason", columnDefinition = "TEXT", updatable = false)
    private String reason;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;



    public enum MovementType {
        REPLENISHMENT, // Пополнение (приемка)
        SHIPMENT,      // Отгрузка
        ADJUSTMENT     // Корректировка (инвентаризация/брак)
    }
}