package com.cetus.shulkercrm.logistics.internal.service;

import com.cetus.shulkercrm.logistics.api.TransportServiceInterface;
import com.cetus.shulkercrm.logistics.api.dto.TransportCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.TransportResponse;
import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouse;
import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouseTransport;
import com.cetus.shulkercrm.logistics.internal.entity.Transport;
import com.cetus.shulkercrm.logistics.internal.repository.OwnWarehouseRepository;
import com.cetus.shulkercrm.logistics.internal.repository.OwnWarehouseTransportRepository;
import com.cetus.shulkercrm.logistics.internal.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportService implements  TransportServiceInterface {


    private final TransportRepository transportRepository;
    private final OwnWarehouseTransportRepository ownWarehouseTransportRepository;
    private final OwnWarehouseRepository ownWarehouseRepository;

    @Override
    @Transactional
    public TransportResponse createTransport(TransportCreateRequest request) {
        log.info("createTransport {}", request);
        Transport transport = Transport.builder()
                .transportType(request.transportType())
                .name(request.name())
                .maxWeightKg(request.maxWeightKg())
                .maxVolumeM3(request.maxVolumeM3())
                .speedKmH(request.speedKmH())
                .costPerKm(request.costPerKm())
                .build();
        Transport savedTransport = transportRepository.save(transport);
    return mapToResponse(savedTransport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportResponse>  getAllTransports() {
        log.info("getAllTransports");
        List<Transport> transports = transportRepository.findAll();
        return transports.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TransportResponse getTransportById(Long id) {
        log.info("getTransportById {}", id);
        Transport transport = transportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transport with id " + id + " not found"));
        return mapToResponse(transport);
    }

    @Override
    @Transactional
    public TransportResponse updateTransportById(Long id, TransportCreateRequest request) {
        log.info("updateTransportById {}", id);
        Transport transport = transportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transport with id " + id + " not found"));
        transport.setName(request.name());
        transport.setMaxWeightKg(request.maxWeightKg());
        transport.setMaxVolumeM3(request.maxVolumeM3());
        transport.setSpeedKmH(request.speedKmH());
        transport.setCostPerKm(request.costPerKm());
        return mapToResponse(transportRepository.save(transport));
    }

    @Override
    @Transactional
    public void deleteTransportById(Long id) {
        log.info("deleteTransportById {}", id);

        //АНАЛОГИЧНАЯ СИТУАЦИЯ

    }


    @Override
    @Transactional
    public void linkTransport(Long transportId, Long warehouseId) {
        log.info("linkTransport transportId:{}, warehouseId:{}", transportId, warehouseId);

        if (ownWarehouseTransportRepository.existsByTransportIdAndOwnWarehouseId(transportId, warehouseId)) {
            log.info("Link already exists, skipping...");
            return;
        }

        Transport transport = transportRepository.findById(transportId)
                .orElseThrow(() -> new EntityNotFoundException("Transport with id " + transportId + " not found"));

        OwnWarehouse warehouse = ownWarehouseRepository.getReferenceById(warehouseId);

        OwnWarehouseTransport link = new OwnWarehouseTransport();
        link.setTransport(transport);
        link.setOwnWarehouse(warehouse);

        ownWarehouseTransportRepository.save(link);
    }

    @Override
    @Transactional
    public void unlinkTransport(Long transportId, Long warehouseId) {
        log.info("unlinkTransport transportId:{}, warehouseId:{}", transportId, warehouseId);

        ownWarehouseTransportRepository.deleteByTransportIdAndOwnWarehouseId(transportId, warehouseId);
    }


    private TransportResponse mapToResponse(Transport entity) {
        return new TransportResponse(
                entity.getId(),
                entity.getTransportType(),
                entity.getName(),
                entity.getMaxWeightKg(),
                entity.getMaxVolumeM3(),
                entity.getSpeedKmH(),
                entity.getCostPerKm(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }


}
