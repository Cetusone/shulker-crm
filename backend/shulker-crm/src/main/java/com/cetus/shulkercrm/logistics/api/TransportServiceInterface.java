package com.cetus.shulkercrm.logistics.api;

import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;

import java.util.List;

public interface TransportServiceInterface {
    public TransportResponse createTransport(TransportCreateRequest request);
    public List<TransportResponse> getAllTransports();
    public TransportResponse getTransportById(Long id);
    public TransportResponse updateTransportById(Long id, TransportCreateRequest request);
    public void deleteTransportById(Long id);

    public void linkTransport(Long transportId, Long warehouseId);
    public void unlinkTransport(Long transportId, Long warehouseId);
}
