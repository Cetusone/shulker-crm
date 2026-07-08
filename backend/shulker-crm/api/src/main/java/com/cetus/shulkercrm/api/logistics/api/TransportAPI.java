package com.cetus.shulkercrm.api.logistics.api;

import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/transports")
public interface TransportAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransportResponse createTransport(@RequestBody @Valid TransportCreateRequest request);

    @GetMapping
    Page<TransportResponse> getAllTransports(Pageable pageable);

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
