package com.cetus.shulkercrm.api.routing.dto;

import com.cetus.shulkercrm.api.logistics.dto.TransportType;

import java.math.BigDecimal;

public record DeliveryOptionResponse(
        Long sourceWarehouseId,
        String sourceWarehouseName,
        Long transportId,
        String transportName,
        TransportType transportType,
        Integer availableQuantity,
        BigDecimal distanceKm,
        BigDecimal totalWeightKg,
        BigDecimal totalVolumeM3,
        Integer requiredTrips,
        BigDecimal estimatedTimeHours,
        BigDecimal estimatedCost
) {}
