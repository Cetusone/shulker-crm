package com.cetus.shulkercrm.service.logistics.controller;

import com.cetus.shulkercrm.api.logistics.api.TransportAPI;
import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import com.cetus.shulkercrm.service.logistics.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransportController implements TransportAPI {

    private final TransportService transportService;

    @Override
    public TransportResponse createTransport(TransportCreateRequest request){
        log.info("createTransport {}", request);
        return transportService.createTransport(request);
    }

    @Override
    public Page<TransportResponse> getAllTransports(Pageable pageable){
        log.info("getAllTransports");
        return transportService.getAllTransports(pageable);
    }

    @Override
    public TransportResponse getTransportById(Long id){
        log.info("getTransportById {}", id);
        return transportService.getTransportById(id);
    }

    @Override
    public TransportResponse updateTransportById(Long id, TransportCreateRequest request){
        log.info("updateTransportById {}", request);
        return transportService.updateTransportById(id, request);
    }

    @Override
    public void deleteTransportById(Long id){
        log.info("deleteTransportById {}", id);
        transportService.deleteTransportById(id);
    }

    @Override
    public void linkTransport(Long transportId, Long warehouseId){
        log.info("linkTransportById {}", transportId);
        transportService.linkTransport(transportId, warehouseId);
    }

    @Override
    public void unlinkTransport(Long transportId, Long warehouseId){
        log.info("unlinkTransportById {}", transportId);
        transportService.unlinkTransport(transportId, warehouseId);
    }

}
