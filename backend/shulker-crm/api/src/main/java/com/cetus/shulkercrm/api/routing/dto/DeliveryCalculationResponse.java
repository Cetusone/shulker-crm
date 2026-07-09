package com.cetus.shulkercrm.api.routing.dto;

import java.util.List;

public record DeliveryCalculationResponse(
        DeliveryPreference appliedPreference,
        Long partnerWarehouseId,
        Long productId,
        Integer requestedQuantity,
        List<DeliveryOptionResponse> options
) {}
