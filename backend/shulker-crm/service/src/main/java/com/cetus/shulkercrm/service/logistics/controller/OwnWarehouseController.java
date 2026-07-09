package com.cetus.shulkercrm.service.logistics.controller;

import com.cetus.shulkercrm.api.logistics.api.OwnWarehouseAPI;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseResponse;
import com.cetus.shulkercrm.service.logistics.service.OwnWarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OwnWarehouseController implements OwnWarehouseAPI {

    private final OwnWarehouseService ownWareHouseService;

    @Override
    public OwnWarehouseResponse createWareHouse(OwnWarehouseCreateRequest request) {
        log.info("createWareHouse {}", request);
        return ownWareHouseService.createWarehouse(request);
    }

    @Override
    public Page<OwnWarehouseResponse> getAllWarehouses(Pageable pageable) {
        log.info("getAllWarehouses");
        return ownWareHouseService.getAllWarehouse(pageable);
    }

    @Override
    public OwnWarehouseResponse getWareHouseById(Long id) {
        log.info("getWareHouseById {}", id);
        return ownWareHouseService.getWarehouseById(id);
    }

    @Override
    public OwnWarehouseResponse updateWareHouseById(Long id, OwnWarehouseCreateRequest request) {
        log.info("updateWareHouseById {}, {}", id, request);
        return ownWareHouseService.updateWarehouse(id, request);
    }

    @Override
    public void deleteWareHouseById(Long id) {
        log.info("deleteWareHouseById {}", id);
        ownWareHouseService.deleteWareHouseById(id);
    }

}
