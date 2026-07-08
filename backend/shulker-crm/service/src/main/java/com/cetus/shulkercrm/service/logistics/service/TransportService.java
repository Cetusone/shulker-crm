package com.cetus.shulkercrm.service.logistics.service;


import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import com.cetus.shulkercrm.service.logistics.entity.OwnWarehouse;
import com.cetus.shulkercrm.service.logistics.entity.OwnWarehouseTransport;
import com.cetus.shulkercrm.service.logistics.entity.Transport;
import com.cetus.shulkercrm.service.logistics.repository.OwnWarehouseRepository;
import com.cetus.shulkercrm.service.logistics.repository.OwnWarehouseTransportRepository;
import com.cetus.shulkercrm.service.logistics.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class TransportService {


    private final TransportRepository transportRepository;
    private final OwnWarehouseTransportRepository ownWarehouseTransportRepository;
    private final OwnWarehouseRepository ownWarehouseRepository;

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


    @Transactional(readOnly = true)
    public Page<TransportResponse>  getAllTransports(Pageable pageable) {
        log.info("getAllTransports");
        Page<Transport> transports = transportRepository.findAll(pageable);
        return transports.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public TransportResponse getTransportById(Long id) {
        log.info("getTransportById {}", id);
        Transport transport = transportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transport with id " + id + " not found"));
        return mapToResponse(transport);
    }

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


    @Transactional
    public void deleteTransportById(Long id) {
        log.info("deleteTransportById {}", id);
        transportRepository.deleteById(id);

    }



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
