package com.cetus.shulkercrm.api.logistics.api;

import com.cetus.shulkercrm.api.common.dto.PageResponse;
import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/transports")
public interface TransportAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransportResponse createTransport(@RequestBody @Valid TransportCreateRequest request);

    @GetMapping
    PageResponse<TransportResponse> getAllTransports(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size);


    @GetMapping("/{id}")
    TransportResponse getTransportById(@PathVariable Long id);

    @PutMapping("/{id}")
    TransportResponse updateTransportById(@PathVariable Long id, @RequestBody @Valid TransportCreateRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTransportById(@PathVariable Long id);

    @PostMapping("/{transportId}/warehouses/{warehouseId}")
    void linkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId);

    @DeleteMapping("/{transportId}/warehouses/{warehouseId}")
    void unlinkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId);

}
