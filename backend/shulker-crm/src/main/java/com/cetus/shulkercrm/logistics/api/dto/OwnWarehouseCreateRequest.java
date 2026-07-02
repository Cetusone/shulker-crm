package com.cetus.shulkercrm.logistics.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record OwnWarehouseCreateRequest(

        @NotBlank(message = "Название объекта не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String name,

        String address,

        @NotNull(message = "Широта обязательна")
        @DecimalMin(value = "-90.0", message = "Широта не может быть меньше -90")
        @DecimalMax(value = "90.0", message = "Широта не может быть больше 90")
        BigDecimal latitude,

        @NotNull(message = "Долгота обязательна")
        @DecimalMin(value = "-180.0", message = "Долгота не может быть меньше -180")
        @DecimalMax(value = "180.0", message = "Долгота не может быть больше 180")
        BigDecimal longitude,

        @NotEmpty(message = "Склад должен поддерживать хотя бы один вид транспорта")
        Set<Long> transportIds
) {}