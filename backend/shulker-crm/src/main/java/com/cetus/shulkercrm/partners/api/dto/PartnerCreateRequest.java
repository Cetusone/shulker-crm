package com.cetus.shulkercrm.partners.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PartnerCreateRequest(

        @NotBlank(message = "Название компании обязательно")
        @Size(max = 255)
        String name,

        @NotBlank(message = "API-ключ обязателен")
        @Size(max = 100)
        String apiKey,

        @Email(message = "Некорректный формат email")
        @Size(max = 255)
        String contactEmail,

        Boolean isActive
) {}