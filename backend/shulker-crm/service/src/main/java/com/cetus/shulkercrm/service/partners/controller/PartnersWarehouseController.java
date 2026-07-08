package com.cetus.shulkercrm.service.partners.controller;


import com.cetus.shulkercrm.api.partners.api.PartnersWarehouseAPI;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.service.partners.service.PartnerWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PartnersWarehouseController implements PartnersWarehouseAPI {

    private final PartnerWarehouseService warehouseService;

    @Override
    public PartnerWarehouseResponse createWarehouse(long partnerId, PartnerWarehouseCreateRequest request) {
        request.setPartnerId(partnerId);
        return warehouseService.createWarehouse(partnerId, request);
    }

    @Override
    public Page<PartnerWarehouseResponse> getWarehouses(long partnerId, Pageable pageable) {
        return warehouseService.getWarehouses(partnerId, pageable);
    }

    @Override
    public PartnerWarehouseResponse getWarehouseById(long partnerId, long warehouseId) {
        return warehouseService.getWarehouseById(partnerId, warehouseId);
    }

    @Override
    public PartnerWarehouseResponse updateWarehouse(long partnerId, long warehouseId, PartnerWarehouseCreateRequest request) {
        request.setPartnerId(partnerId);
        return warehouseService.updateWarehouse(partnerId, warehouseId, request);
    }

    @Override
    public void deleteWarehouse(long partnerId,long warehouseId) {
        warehouseService.deleteWarehouse(partnerId, warehouseId);
    }
}