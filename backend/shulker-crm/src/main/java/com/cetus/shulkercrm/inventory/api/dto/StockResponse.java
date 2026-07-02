package com.cetus.shulkercrm.inventory.api.dto;

import java.time.LocalDateTime;

public record StockResponse(
        Long id,
        Long ownWarehouseId,
        Long productId,
        Integer quantity,
        Integer reservedQuantity,
        LocalDateTime updatedAt
) {
}
