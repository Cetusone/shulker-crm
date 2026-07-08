package com.cetus.shulkercrm.api.routing.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeliveryOptionRequest(

        @NotNull(message = "ID товара обязателен")
        Long productId,

        @NotNull(message = "Количество обязательно")
        @Positive(message = "Количество должно быть больше 0")
        Integer quantity,

        @NotNull
        Long partnerId,

        @NotNull(message = "ID склада партнёра обязателен")
        Long partnerWarehouseId,

        @NotNull(message = "Условие доставки обязательно (FASTEST или CHEAPEST)")
        DeliveryPreference preference
) {}
