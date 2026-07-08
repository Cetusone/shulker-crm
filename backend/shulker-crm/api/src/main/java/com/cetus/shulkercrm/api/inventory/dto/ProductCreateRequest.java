package com.cetus.shulkercrm.api.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateRequest(
        @NotBlank(message = "Название не может быть пустым")
        String name,

        String description,

        @NotBlank(message = "Артикул не может быть пустым")
        String sku,

        @NotNull
        @Positive(message = "Вес должен быть больше 0")
        BigDecimal weightKg,

        @NotNull
        @Positive(message = "Объем должен быть больше 0")
        BigDecimal volumeM3,

        List<CharacteristicDto> characteristics
) {
    public record CharacteristicDto(
            @NotBlank(message = "Название атрибута обязательно")
            String attributeName,

            @NotBlank(message = "Значение атрибута обязательно")
            String attributeValue
    ) {}
}