package com.cetus.shulkercrm.api.logistics.api;

import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.OwnWarehouseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/own-warehouses")
public interface OwnWarehouseAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OwnWarehouseResponse createWareHouse(@RequestBody @Valid OwnWarehouseCreateRequest request);

    @GetMapping
    PageResponse<OwnWarehouseResponse> getAllWarehouses(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);

    @GetMapping("/{id}")
    OwnWarehouseResponse getWareHouseById(@PathVariable Long id);

    @PutMapping("/{id}")
    OwnWarehouseResponse updateWareHouseById(@PathVariable Long id, @RequestBody @Valid OwnWarehouseCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWareHouseById(@PathVariable Long id);

}
