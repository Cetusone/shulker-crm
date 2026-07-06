package com.cetus.shulkercrm.logistics.internal.service;

import com.cetus.shulkercrm.logistics.api.OwnWarehouseServiceInterface;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseCreateRequest;
import com.cetus.shulkercrm.logistics.api.dto.OwnWarehouseResponse;
import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouse;
import com.cetus.shulkercrm.logistics.internal.entity.OwnWarehouseTransport;
import com.cetus.shulkercrm.logistics.internal.entity.Transport;
import com.cetus.shulkercrm.logistics.internal.repository.OwnWarehouseRepository;
import com.cetus.shulkercrm.logistics.internal.repository.OwnWarehouseTransportRepository;
import com.cetus.shulkercrm.logistics.internal.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnWarehouseService implements OwnWarehouseServiceInterface {

    private final OwnWarehouseRepository ownWarehouseRepository;
    private final TransportRepository transportRepository;
    private final OwnWarehouseTransportRepository ownWarehouseTransportRepository;

    @Override
    @Transactional
    public OwnWarehouseResponse createWarehouse(OwnWarehouseCreateRequest request) {
        log.info("Creating warehouse: {}", request);

        OwnWarehouse warehouseToSave = OwnWarehouse.builder()
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();

        final OwnWarehouse savedWarehouse = ownWarehouseRepository.save(warehouseToSave);

        List<Transport> transports = transportRepository.findAllById(request.transportIds());

        if (transports.isEmpty()) {
            throw new IllegalArgumentException("Не найден ни один вид транспорта из переданного списка");
        }
        if (transports.size() != request.transportIds().size()) {
            throw new IllegalArgumentException("Некоторые из указанных видов транспорта не существуют в базе");
        }

        List<OwnWarehouseTransport> links = transports.stream()
                .map(transport -> {
                    OwnWarehouseTransport link = new OwnWarehouseTransport();
                    link.setOwnWarehouse(savedWarehouse);
                    link.setTransport(transport);
                    return link;
                })
                .toList();
        ownWarehouseTransportRepository.saveAll(links);
        return mapToResponse(savedWarehouse, transports);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OwnWarehouseResponse> getAllWarehouse(Pageable pageable) {
        log.info("getAllWareHouse");
        Page<OwnWarehouse> warehouses = ownWarehouseRepository.findAll(pageable);
        if (warehouses.isEmpty()) {
            return Page.empty(pageable);
        }
        List<OwnWarehouse> warehousesOnPage = warehouses.getContent();
        List<OwnWarehouseTransport> links = ownWarehouseTransportRepository.findByOwnWarehouseIn(warehousesOnPage);
        Map<Long, List<Transport>> transportsByWarehouseId = links.stream()
                .collect(Collectors.groupingBy(
                        link -> link.getOwnWarehouse().getId(),
                        Collectors.mapping(OwnWarehouseTransport::getTransport, Collectors.toList())
                ));
        return warehouses.map(warehouse -> {
            List<Transport> transports = transportsByWarehouseId.getOrDefault(warehouse.getId(), List.of());
            return mapToResponse(warehouse, transports);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public OwnWarehouseResponse getWarehouseById(Long id) {
        log.info("getWarehouseById {}", id);
        OwnWarehouse warehouse = ownWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Склад с id:" + id + "не найден"));
        List<OwnWarehouseTransport> links = ownWarehouseTransportRepository.findAllByOwnWarehouseId(id);
        List<Transport> transports = links.stream()
                .map(OwnWarehouseTransport::getTransport)
                .toList();
        return mapToResponse(warehouse, transports);

    }

    @Override
    @Transactional
    public OwnWarehouseResponse updateWarehouse(Long id, OwnWarehouseCreateRequest request) {
        log.info("updateWarehouseById {}", id);
            // 1. Ищем существующий склад
        OwnWarehouse warehouse = ownWarehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Склад с id: " + id + " не найден"));

        List<Transport> newTransports = transportRepository.findAllById(request.transportIds());

        if (newTransports.isEmpty()) {
            throw new IllegalArgumentException("Не найден ни один вид транспорта из переданного списка");
        }
        if (newTransports.size() != request.transportIds().size()) {
            throw new IllegalArgumentException("Некоторые из указанных видов транспорта не существуют в базе");
        }

        warehouse.setName(request.name());
        warehouse.setAddress(request.address());
        warehouse.setLatitude(request.latitude());
        warehouse.setLongitude(request.longitude());
        ownWarehouseTransportRepository.deleteAllByOwnWarehouseId(id);
        ownWarehouseTransportRepository.flush();

        List<OwnWarehouseTransport> newLinks = newTransports.stream()
            .map(transport -> {
                OwnWarehouseTransport link = new OwnWarehouseTransport();
                link.setOwnWarehouse(warehouse);
                link.setTransport(transport);
                return link;
                })
            .toList();

            ownWarehouseTransportRepository.saveAll(newLinks);

            return mapToResponse(warehouse, newTransports);

    }

    @Override
    @Transactional
    public void deleteWareHouseById(Long id) {
        log.info("deleteWareHouseById {}", id);

        //БЛЯ ТУТ ТОЧНО ТАКЖЕ
        //ПОТОМ КОГДА-НИБУДЬ
    }


    private OwnWarehouseResponse mapToResponse(OwnWarehouse warehouse, List<Transport> transports) {
            List<OwnWarehouseResponse.TransportShortDto> transportDtos = transports.stream()
                    .map(t -> new OwnWarehouseResponse.TransportShortDto(
                            t.getId(),
                            t.getTransportType().name(),
                            t.getName()
                    ))
                    .toList();

            return new OwnWarehouseResponse(
                    warehouse.getId(),
                    warehouse.getName(),
                    warehouse.getAddress(),
                    warehouse.getLatitude(),
                    warehouse.getLongitude(),
                    warehouse.getIsActive(),
                    warehouse.getCreatedAt(),
                    warehouse.getUpdatedAt(),
                    transportDtos
            );
    }
}
