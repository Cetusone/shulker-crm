package com.cetus.shulkercrm.api.logistics.dto;

public record TransportShortDto(
        Long id,
        String transportType, // AUTO, RAILWAY, AVIATION
        String name
) {}