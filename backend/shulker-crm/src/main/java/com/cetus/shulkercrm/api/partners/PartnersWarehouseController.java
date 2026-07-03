package com.cetus.shulkercrm.api.partners;

import com.cetus.shulkercrm.partners.api.PartnerWarehouseServiceInterface;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.partners.api.dto.PartnerWarehouseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners/{partnerId}/warehouses")
@RequiredArgsConstructor
public class PartnersWarehouseController {

    private final PartnerWarehouseServiceInterface warehouseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerWarehouseResponse createWarehouse(
            @PathVariable long partnerId,
            @Valid @RequestBody PartnerWarehouseCreateRequest request
    ) {
        request.setPartnerId(partnerId);
        return warehouseService.createWarehouse(partnerId, request);
    }

    @GetMapping
    public List<PartnerWarehouseResponse> getWarehouses(@PathVariable long partnerId) {
        return warehouseService.getWarehouses(partnerId);
    }

    @GetMapping("/{warehouseId}")
    public PartnerWarehouseResponse getWarehouseById(
            @PathVariable long partnerId,
            @PathVariable long warehouseId
    ) {
        return warehouseService.getWarehouseById(partnerId, warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public PartnerWarehouseResponse updateWarehouse(
            @PathVariable long partnerId,
            @PathVariable long warehouseId,
            @Valid @RequestBody PartnerWarehouseCreateRequest request
    ) {
        request.setPartnerId(partnerId);
        return warehouseService.updateWarehouse(partnerId, warehouseId, request);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouse(
            @PathVariable long partnerId,
            @PathVariable long warehouseId
    ) {
        warehouseService.deleteWarehouse(partnerId, warehouseId);
    }
}