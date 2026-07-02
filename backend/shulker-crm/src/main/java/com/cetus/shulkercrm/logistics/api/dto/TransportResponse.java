package com.cetus.shulkercrm.logistics.api.dto;

import com.cetus.shulkercrm.logistics.internal.entity.TransportType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransportResponse(
        Long id,
        TransportType transportType,
        String name,
        BigDecimal maxWeightKg,
        BigDecimal maxVolumeM3,
        BigDecimal speedKmH,
        BigDecimal costPerKm,
        Instant createdAt,
        Instant updatedAt
) {}