package com.cetus.shulkercrm.api.partners.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PartnerWarehouseResponse(
        Long id,
        Long partnerId,
        String name,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,

        Boolean acceptsLand,
        Boolean acceptsSea,
        Boolean acceptsAir,

        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}