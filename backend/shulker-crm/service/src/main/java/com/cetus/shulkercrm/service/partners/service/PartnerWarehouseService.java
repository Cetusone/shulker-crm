package com.cetus.shulkercrm.service.partners.service;


import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import com.cetus.shulkercrm.service.partners.entity.PartnerWarehouse;
import com.cetus.shulkercrm.service.partners.repository.PartnerRepository;
import com.cetus.shulkercrm.service.partners.repository.PartnerWarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerWarehouseService {
    private final PartnerWarehouseRepository partnerWarehouseRepository;
    private final PartnerRepository partnerRepository;

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

    @Transactional(readOnly = true)
    public Page<PartnerWarehouseResponse> getWarehouses(long partnerId, Pageable pageable) {
        log.debug("Запрос списка складов для партнёра ID: {}", partnerId);

        Page<PartnerWarehouse> warehouses = partnerWarehouseRepository.findAllByPartnerId(partnerId, pageable);
        return warehouses.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId) {
        log.debug("Запрос склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        PartnerWarehouse warehouse = getWarehouseAndVerifyPartner(partnerId, warehouseId);
        return mapToResponse(warehouse);
    }

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

    @Transactional
    public void deleteWarehouse(long partnerId, long warehouseId) {
        log.debug("Удаление (деактивация) склада ID: {} для партнёра ID: {}", warehouseId, partnerId);
        partnerWarehouseRepository.deleteById(warehouseId);

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
