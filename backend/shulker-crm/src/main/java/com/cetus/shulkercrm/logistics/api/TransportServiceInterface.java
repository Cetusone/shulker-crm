package com.cetus.shulkercrm.logistics.api;

import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransportServiceInterface {
    public TransportResponse createTransport(TransportCreateRequest request);
    public Page<TransportResponse> getAllTransports(Pageable pageable);
    public TransportResponse getTransportById(Long id);
    public TransportResponse updateTransportById(Long id, TransportCreateRequest request);
    public void deleteTransportById(Long id);

    public void linkTransport(Long transportId, Long warehouseId);
    public void unlinkTransport(Long transportId, Long warehouseId);
}
