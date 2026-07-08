package com.cetus.shulkercrm.service.routing.service;

import com.cetus.shulkercrm.api.inventory.api.ProductAPI;
import com.cetus.shulkercrm.api.inventory.api.StockAPI;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.api.logistics.api.OwnWarehouseAPI;
import com.cetus.shulkercrm.api.logistics.api.TransportAPI;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseResponse;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import com.cetus.shulkercrm.api.logistics.dto.TransportType;
import com.cetus.shulkercrm.api.partners.api.PartnersWarehouseAPI;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.api.routing.dto.DeliveryCalculationResponse;
import com.cetus.shulkercrm.api.routing.dto.DeliveryOptionRequest;
import com.cetus.shulkercrm.api.routing.dto.DeliveryOptionResponse;
import com.cetus.shulkercrm.api.routing.dto.DeliveryPreference;
import com.cetus.shulkercrm.service.routing.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoutingService {

    private final ProductAPI productAPI;
    private final PartnersWarehouseAPI partnerWarehouseAPI;
    private final OwnWarehouseAPI ownWarehouseAPI;
    private final StockAPI stockAPI;
    private final TransportAPI transportAPI;

    public DeliveryCalculationResponse calculateDeliveryOptions(DeliveryOptionRequest request) {
        log.info("calculateDeliveryOptions: productId={}, quantity={}, partnerWarehouseId={}, preference={}",
                request.productId(), request.quantity(), request.partnerWarehouseId(), request.preference());

        ProductResponse product = productAPI.getProductById(request.productId());

        PartnerWarehouseResponse destination = partnerWarehouseAPI.getWarehouseById(
                request.partnerId(), request.partnerWarehouseId());

        Set<TransportType> acceptedTypes = resolveAcceptedTransportTypes(destination);
        if (acceptedTypes.isEmpty()) {
            log.warn("Склад партнёра {} не принимает ни один тип транспорта", destination.id());
            return emptyResponse(request);
        }

        List<DeliveryOptionResponse> options = new ArrayList<>();

        List<OwnWarehouseResponse> warehouses = ownWarehouseAPI.getAllWarehouses(Pageable.unpaged()).getContent();

        for (OwnWarehouseResponse warehouse : warehouses) {

            List<StockResponse> stocks = stockAPI.getAllStocks(warehouse.id(), Pageable.unpaged()).getContent();

            Optional<StockResponse> productStock = stocks.stream()
                    .filter(s -> s.productId().equals(request.productId()))
                    .findFirst();

            if (productStock.isEmpty()) {
                continue;
            }

            int availableQuantity = productStock.get().quantity() - productStock.get().reservedQuantity();
            if (availableQuantity < request.quantity()) {
                continue;
            }

            for (OwnWarehouseResponse.TransportShortDto transportShort : warehouse.transports()) {
                TransportType type = TransportType.valueOf(transportShort.transportType());

                if (!acceptedTypes.contains(type)) {
                    continue;
                }

                TransportResponse transport = transportAPI.getTransportById(transportShort.id());

                DeliveryOptionResponse option = buildOption(
                        warehouse, transport, destination, product,
                        request.quantity(), availableQuantity
                );
                options.add(option);
            }
        }

        if (options.isEmpty()) {
            log.info("Нет доступных вариантов доставки для товара {} (запрошено: {})",
                    request.productId(), request.quantity());
            return emptyResponse(request);
        }

        sortByPreference(options, request.preference());

        log.info("Рассчитано {} вариантов доставки", options.size());

        return new DeliveryCalculationResponse(
                request.preference(),
                request.partnerWarehouseId(),
                request.productId(),
                request.quantity(),
                options
        );
    }

    private Set<TransportType> resolveAcceptedTransportTypes(PartnerWarehouseResponse destination) {
        Set<TransportType> accepted = new HashSet<>();

        if (Boolean.TRUE.equals(destination.acceptsLand())) {
            accepted.add(TransportType.AUTO);
            accepted.add(TransportType.RAILWAY);
        }
        if (Boolean.TRUE.equals(destination.acceptsAir())) {
            accepted.add(TransportType.AVIATION);
        }

        return accepted;
    }

    private DeliveryOptionResponse buildOption(OwnWarehouseResponse source,
                                               TransportResponse transport,
                                               PartnerWarehouseResponse destination,
                                               ProductResponse product,
                                               int requestedQuantity,
                                               int availableQuantity) {

        double distanceRaw = GeoUtils.haversineDistance(
                source.latitude(), source.longitude(),
                destination.latitude(), destination.longitude()
        );
        BigDecimal distanceKm = BigDecimal.valueOf(distanceRaw).setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalWeightKg = product.weightKg()
                .multiply(BigDecimal.valueOf(requestedQuantity))
                .setScale(4, RoundingMode.HALF_UP);

        BigDecimal totalVolumeM3 = product.volumeM3()
                .multiply(BigDecimal.valueOf(requestedQuantity))
                .setScale(6, RoundingMode.HALF_UP);

        int tripsByWeight = ceilDiv(totalWeightKg, transport.maxWeightKg());
        int tripsByVolume = ceilDiv(totalVolumeM3, transport.maxVolumeM3());
        int requiredTrips = Math.max(Math.max(tripsByWeight, tripsByVolume), 1);

        BigDecimal estimatedTimeHours = distanceKm
                .divide(transport.speedKmH(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(requiredTrips))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal estimatedCost = distanceKm
                .multiply(transport.costPerKm())
                .multiply(BigDecimal.valueOf(requiredTrips))
                .setScale(2, RoundingMode.HALF_UP);

        return new DeliveryOptionResponse(
                source.id(),
                source.name(),
                transport.id(),
                transport.name(),
                transport.transportType(),
                availableQuantity,
                distanceKm,
                totalWeightKg,
                totalVolumeM3,
                requiredTrips,
                estimatedTimeHours,
                estimatedCost
        );
    }

    private int ceilDiv(BigDecimal total, BigDecimal capacity) {
        if (capacity.compareTo(BigDecimal.ZERO) <= 0) {
            return 1;
        }
        return total.divide(capacity, 0, RoundingMode.CEILING).intValue();
    }

    private void sortByPreference(List<DeliveryOptionResponse> options,
                                  DeliveryPreference preference) {
        Comparator<DeliveryOptionResponse> comparator;

        if (preference == DeliveryPreference.FASTEST) {
            comparator = Comparator
                    .comparing(DeliveryOptionResponse::estimatedTimeHours)
                    .thenComparing(DeliveryOptionResponse::estimatedCost);
        } else {
            comparator = Comparator
                    .comparing(DeliveryOptionResponse::estimatedCost)
                    .thenComparing(DeliveryOptionResponse::estimatedTimeHours);
        }

        options.sort(comparator);
    }

    private DeliveryCalculationResponse emptyResponse(DeliveryOptionRequest request) {
        return new DeliveryCalculationResponse(
                request.preference(),
                request.partnerWarehouseId(),
                request.productId(),
                request.quantity(),
                List.of()
        );
    }
}