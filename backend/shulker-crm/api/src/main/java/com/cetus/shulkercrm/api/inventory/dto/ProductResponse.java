package com.cetus.shulkercrm.api.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        BigDecimal weightKg,
        BigDecimal volumeM3,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CharacteristicResponse> characteristics
) {
    public record CharacteristicResponse(
            Long id,
            String attributeName,
            String attributeValue
    ) {}
}
