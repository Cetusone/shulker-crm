package com.cetus.shulkercrm.service.routing.controller;


import com.cetus.shulkercrm.api.routing.dto.DeliveryCalculationResponse;
import com.cetus.shulkercrm.api.routing.dto.DeliveryOptionRequest;
import com.cetus.shulkercrm.service.routing.service.RoutingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/routing")
public class RoutingController {

    private final RoutingService routingService;

    @PostMapping("/calculate")
    public DeliveryCalculationResponse calculateDeliveryOptions(
            @Valid @RequestBody DeliveryOptionRequest request) {
        log.info("calculateDeliveryOptions {}", request);
        return routingService.calculateDeliveryOptions(request);
    }
}
