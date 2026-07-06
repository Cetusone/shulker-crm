package com.cetus.shulkercrm.logistics.api.controller;

import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseResponse;
import com.cetus.shulkercrm.logistics.internal.service.OwnWarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/own-warehouses")
@Slf4j
@RequiredArgsConstructor
public class OwnWarehouseController {

    private final OwnWarehouseService ownWareHouseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnWarehouseResponse createWareHouse(@RequestBody @Valid OwnWarehouseCreateRequest request) {
        log.info("createWareHouse {}", request);
        return ownWareHouseService.createWarehouse(request);
    }

    @GetMapping
    public Page<OwnWarehouseResponse> getAllWarehouses(Pageable pageable) {
        log.info("getAllWarehouses");
        return ownWareHouseService.getAllWarehouse(pageable);
    }

    @GetMapping("/{id}")
    public OwnWarehouseResponse getWareHouseById(@PathVariable Long id) {
        log.info("getWareHouseById {}", id);
        return ownWareHouseService.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public OwnWarehouseResponse updateWareHouseById(@PathVariable Long id, @RequestBody @Valid OwnWarehouseCreateRequest request) {
        log.info("updateWareHouseById {}, {}", id, request);
        return ownWareHouseService.updateWarehouse(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWareHouseById(@PathVariable Long id) {
        log.info("deleteWareHouseById {}", id);
        ownWareHouseService.deleteWareHouseById(id);
    }

}
