package com.cetus.shulkercrm.logistics.internal.entity;

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
    }
