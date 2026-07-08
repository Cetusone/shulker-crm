package com.cetus.shulkercrm.api.partners.api;


import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/partners/{partnerId}/warehouses")
public interface PartnersWarehouseAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PartnerWarehouseResponse createWarehouse(@PathVariable long partnerId, @Valid @RequestBody PartnerWarehouseCreateRequest request);

    @GetMapping
    Page<PartnerWarehouseResponse> getWarehouses(@PathVariable long partnerId, Pageable pageable);

    @GetMapping("/{warehouseId}")
    PartnerWarehouseResponse getWarehouseById(@PathVariable long partnerId, @PathVariable long warehouseId);

    @PutMapping("/{warehouseId}")
    PartnerWarehouseResponse updateWarehouse(@PathVariable long partnerId,
                                             @PathVariable long warehouseId,
                                             @Valid @RequestBody PartnerWarehouseCreateRequest request);

    @DeleteMapping("/{warehouseId}")
    void deleteWarehouse(@PathVariable long partnerId, @PathVariable long warehouseId);
}
