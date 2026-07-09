package com.cetus.shulkercrm.api.logistics.api;

import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/own-warehouses")
public interface OwnWarehouseAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OwnWarehouseResponse createWareHouse(@RequestBody @Valid OwnWarehouseCreateRequest request);

    @GetMapping
    Page<OwnWarehouseResponse> getAllWarehouses(Pageable pageable);

    @GetMapping("/{id}")
    OwnWarehouseResponse getWareHouseById(@PathVariable Long id);

    @PutMapping("/{id}")
    OwnWarehouseResponse updateWareHouseById(@PathVariable Long id, @RequestBody @Valid OwnWarehouseCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWareHouseById(@PathVariable Long id);

}
