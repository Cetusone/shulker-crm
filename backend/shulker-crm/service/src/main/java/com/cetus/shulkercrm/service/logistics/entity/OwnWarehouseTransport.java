package com.cetus.shulkercrm.service.logistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
    @Table(
            name = "own_warehouse_transports",
            uniqueConstraints = {
                    @UniqueConstraint(
                            name = "uk_warehouse_transport",
                            columnNames = {"own_warehouse_id", "transport_id"}
                    )
            },
            indexes = {
                    @Index(name = "idx_own_warehouse_transports_transport_id", columnList = "transport_id")
            }
    )

@SQLDelete(sql = "UPDATE own_warehouse_transports SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnWarehouseTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "own_warehouse_id", nullable = false)
    private OwnWarehouse ownWarehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;



}
