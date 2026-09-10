package com.cetus.shulkercrm.api.partners.api;


import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/partners/{partnerId}/warehouses")
public interface PartnersWarehouseAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PartnerWarehouseResponse createWarehouse(@PathVariable long partnerId, @Valid @RequestBody PartnerWarehouseCreateRequest request);

    @GetMapping
    PageResponse<PartnerWarehouseResponse> getWarehouses(
            @PathVariable long partnerId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);



    @GetMapping("/{warehouseId}")
    PartnerWarehouseResponse getWarehouseById(@PathVariable long partnerId, @PathVariable long warehouseId);

    @PutMapping("/{warehouseId}")
    PartnerWarehouseResponse updateWarehouse(@PathVariable long partnerId,
                                             @PathVariable long warehouseId,
                                             @Valid @RequestBody PartnerWarehouseCreateRequest request);

    @DeleteMapping("/{warehouseId}")
    void deleteWarehouse(@PathVariable long partnerId, @PathVariable long warehouseId);
}
