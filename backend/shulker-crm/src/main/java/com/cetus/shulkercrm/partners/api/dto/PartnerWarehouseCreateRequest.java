package com.cetus.shulkercrm.partners.api.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartnerWarehouseCreateRequest {

    @NotNull(message = "ID партнёра обязателен")
    private Long partnerId;

    @NotBlank(message = "Название точки доставки обязательно")
    @Size(max = 255)
    private String name;

    private String address;

    @NotNull(message = "Широта обязательна")
    private BigDecimal latitude;

    @NotNull(message = "Долгота обязательна")
    private BigDecimal longitude;

    @NotNull(message = "Укажите доступность наземного транспорта")
    private Boolean acceptsLand;

    @NotNull(message = "Укажите доступность морского транспорта")
    private Boolean acceptsSea;

    @NotNull(message = "Укажите доступность авиатранспорта")
    private Boolean acceptsAir;

    @AssertTrue(message = "Хотя бы один тип транспорта (наземный, морской или авиа) должен быть доступен")
    private boolean isAtLeastOneTransportAvailable() {
        return Boolean.TRUE.equals(acceptsLand) ||
                Boolean.TRUE.equals(acceptsSea) ||
                Boolean.TRUE.equals(acceptsAir);
    }
}