package com.cetus.shulkercrm.service.partners.service;


import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import com.cetus.shulkercrm.service.partners.entity.PartnerWarehouse;
import com.cetus.shulkercrm.service.partners.mapper.PartnerWarehouseMapper;
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
    private final PartnerWarehouseMapper partnerWarehouseMapper;

    @Transactional
    public PartnerWarehouseResponse createWarehouse(long partnerId, PartnerWarehouseCreateRequest request) {
        log.debug("Создание склада для партнёра ID: {}", partnerId);

        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new EntityNotFoundException("Партнёр с ID " + partnerId + " не найден"));

        PartnerWarehouse warehouse = partnerWarehouseMapper.toEntity(request);
        warehouse.setPartner(partner);

        return partnerWarehouseMapper.toResponse(partnerWarehouseRepository.save(warehouse));
    }

    @Transactional(readOnly = true)
    public Page<PartnerWarehouseResponse> getWarehouses(long partnerId, Pageable pageable) {
        log.debug("Запрос списка складов для партнёра ID: {}", partnerId);

        Page<PartnerWarehouse> warehouses = partnerWarehouseRepository.findAllByPartnerId(partnerId, pageable);
        return warehouses.map(partnerWarehouseMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId) {
        log.debug("Запрос склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        PartnerWarehouse warehouse = getWarehouseAndVerifyPartner(partnerId, warehouseId);
        return partnerWarehouseMapper.toResponse(warehouse);
    }

    @Transactional
    public PartnerWarehouseResponse updateWarehouse(long partnerId, long warehouseId, PartnerWarehouseCreateRequest request) {
        log.debug("Обновление склада ID: {} для партнёра ID: {}", warehouseId, partnerId);

        PartnerWarehouse warehouse = getWarehouseAndVerifyPartner(partnerId, warehouseId);

        partnerWarehouseMapper.updateFromRequest(request, warehouse);

        return partnerWarehouseMapper.toResponse(partnerWarehouseRepository.save(warehouse));
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
}
