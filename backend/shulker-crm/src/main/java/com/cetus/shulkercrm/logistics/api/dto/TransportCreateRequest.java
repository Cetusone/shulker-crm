package com.cetus.shulkercrm.logistics.api.dto;

import com.cetus.shulkercrm.logistics.internal.entity.TransportType; // Убедись, что импорт правильный
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransportCreateRequest(

        @NotNull(message = "Тип транспорта обязателен")
        TransportType transportType,

        @NotBlank(message = "Название (модель) не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String name,

        @NotNull(message = "Грузоподъёмность обязательна")
        @Positive(message = "Грузоподъёмность (кг) должна быть больше 0")
        BigDecimal maxWeightKg,

        @NotNull(message = "Вместимость по объёму обязательна")
        @Positive(message = "Вместимость (м³) должна быть больше 0")
        BigDecimal maxVolumeM3,

        @NotNull(message = "Средняя скорость обязательна")
        @Positive(message = "Средняя скорость (км/ч) должна быть больше 0")
        BigDecimal speedKmH,

        @NotNull(message = "Стоимость за километр обязательна")
        @Positive(message = "Стоимость за километр должна быть больше 0")
        BigDecimal costPerKm

) {}