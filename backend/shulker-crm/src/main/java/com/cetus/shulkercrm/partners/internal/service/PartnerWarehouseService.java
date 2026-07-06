package com.cetus.shulkercrm.partners.internal.service;

import com.cetus.shulkercrm.partners.api.PartnerWarehouseServiceInterface;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.partners.internal.entity.Partner;
import com.cetus.shulkercrm.partners.internal.entity.PartnerWarehouse;
import com.cetus.shulkercrm.partners.internal.repository.PartnerRepository;
import com.cetus.shulkercrm.partners.internal.repository.PartnerWarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerWarehouseService implements PartnerWarehouseServiceInterface {
    private final PartnerWarehouseRepository partnerWarehouseRepository;
    private final PartnerRepository partnerRepository;

    @Override
    @Transactional
    public PartnerWarehouseResponse createWarehouse(long partnerId, PartnerWarehouseCreateRequest request) {
        log.debug("Создание склада для партнёра ID: {}", partnerId);

        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + partnerId + " не найден"));

        PartnerWarehouse warehouse = PartnerWarehouse.builder()
                .partner(partner)
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .acceptsLand(request.getAcceptsLand())
                .acceptsSea(request.getAcceptsSea())
                .acceptsAir(request.getAcceptsAir())
                .isActive(true)
                .build();

        return mapToResponse(partnerWarehouseRepository.save(warehouse));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartnerWarehouseResponse> getWarehouses(long partnerId, Pageable pageable) {
        log.debug("Запрос списка складов для партнёра ID: {}", partnerId);

        Page<PartnerWarehouse> warehouses = partnerWarehouseRepository.findAllByPartnerId(partnerId, pageable);
        return warehouses.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId) {
        log.debug("Запрос склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        PartnerWarehouse warehouse = getWarehouseAndVerifyPartner(partnerId, warehouseId);
        return mapToResponse(warehouse);
    }

    @Override
    @Transactional
    public PartnerWarehouseResponse updateWarehouse(long partnerId, long warehouseId, PartnerWarehouseCreateRequest request) {
        log.debug("Обновление склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        PartnerWarehouse warehouse = getWarehouseAndVerifyPartner(partnerId, warehouseId);

        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        warehouse.setLatitude(request.getLatitude());
        warehouse.setLongitude(request.getLongitude());
        warehouse.setAcceptsLand(request.getAcceptsLand());
        warehouse.setAcceptsSea(request.getAcceptsSea());
        warehouse.setAcceptsAir(request.getAcceptsAir());

        return mapToResponse(partnerWarehouseRepository.save(warehouse));
    }

    @Override
    @Transactional
    public void deleteWarehouse(long partnerId, long warehouseId) {
        log.debug("Удаление (деактивация) склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        //  ДОДЕЛАТЬ!

    }

    private PartnerWarehouse getWarehouseAndVerifyPartner(long partnerId, long warehouseId) {
        return partnerWarehouseRepository.findByIdAndPartnerId(warehouseId, partnerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Склад с ID " + warehouseId + " не найден у партнёра " + partnerId));
    }

    private PartnerWarehouseResponse mapToResponse(PartnerWarehouse warehouse) {
        return new PartnerWarehouseResponse(
                warehouse.getId(),
                warehouse.getPartner().getId(),
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                warehouse.getAcceptsLand(),
                warehouse.getAcceptsSea(),
                warehouse.getAcceptsAir(),
                warehouse.getIsActive(),
                warehouse.getCreatedAt(),
                warehouse.getUpdatedAt()
        );
    }
}
