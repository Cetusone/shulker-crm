package com.cetus.shulkercrm.inventory.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockCreateRequest(
        @NotNull(message = "ID товара обязателен")
        Long productId,

        @NotNull(message = "Количество обязательно")
        @Positive(message = "Количество для оприходования должно быть больше 0")
        Integer quantity,

        String reason
) {
}
