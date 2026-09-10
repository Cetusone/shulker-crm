package com.cetus.shulkercrm.api.inventory.dto;

import jakarta.validation.constraints.NotBlank;

public record CharacteristicDto(
        @NotBlank(message = "Название атрибута обязательно")
        String attributeName,

        @NotBlank(message = "Значение атрибута обязательно")
        String attributeValue
) {
}
