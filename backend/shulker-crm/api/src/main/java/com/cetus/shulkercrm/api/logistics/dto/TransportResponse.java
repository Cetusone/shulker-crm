package com.cetus.shulkercrm.api.logistics.dto;

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