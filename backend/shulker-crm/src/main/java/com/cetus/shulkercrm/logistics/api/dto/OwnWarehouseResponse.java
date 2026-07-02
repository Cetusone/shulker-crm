package com.cetus.shulkercrm.logistics.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OwnWarehouseResponse(
        Long id,
        String name,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        List<TransportShortDto> transports
) {

    public record TransportShortDto(
            Long id,
            String transportType, // AUTO, RAILWAY, AVIATION
            String name
    ) {}
}