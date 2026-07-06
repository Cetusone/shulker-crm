package com.cetus.shulkercrm.logistics.api.controller;

import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;
import com.cetus.shulkercrm.logistics.internal.service.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
@Slf4j
@RequiredArgsConstructor
public class TransportController {

    private final TransportService transportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransportResponse createTransport(@RequestBody @Valid TransportCreateRequest request){
        log.info("createTransport {}", request);
        return transportService.createTransport(request);
    }

    @GetMapping
    public Page<TransportResponse> getAllTransports(Pageable pageable){
        log.info("getAllTransports");
        return transportService.getAllTransports(pageable);
    }

    @GetMapping("/{id}")
    public TransportResponse getTransportById(@PathVariable Long id){
        log.info("getTransportById {}", id);
        return transportService.getTransportById(id);
    }

    @PutMapping("/{id}")
    public TransportResponse updateTransportById(@PathVariable Long id, @RequestBody @Valid TransportCreateRequest request){
        log.info("updateTransportById {}", request);
        return transportService.updateTransportById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransportById(@PathVariable Long id){
        log.info("deleteTransportById {}", id);
        transportService.deleteTransportById(id);
    }

    @PostMapping("/{transportId}/warehouses/{warehouseId}")
    public void linkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("linkTransportById {}", transportId);
        transportService.linkTransport(transportId, warehouseId);
    }

    @DeleteMapping("/{transportId}/warehouses/{warehouseId}")
    public void unlinkTransport(@PathVariable Long transportId, @PathVariable Long warehouseId){
        log.info("unlinkTransportById {}", transportId);
        transportService.unlinkTransport(transportId, warehouseId);
    }

}
