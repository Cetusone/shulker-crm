package com.cetus.shulkercrm.partners.api.dto;

import java.time.LocalDateTime;

public record PartnerResponse(
        Long id,
        String name,
        String contactEmail,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
