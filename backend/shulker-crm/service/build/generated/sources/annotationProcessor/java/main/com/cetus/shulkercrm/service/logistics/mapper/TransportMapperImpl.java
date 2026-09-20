package com.cetus.shulkercrm.service.logistics.mapper;

import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import com.cetus.shulkercrm.api.logistics.dto.TransportType;
import com.cetus.shulkercrm.service.logistics.entity.Transport;
import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-20T12:51:26+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class TransportMapperImpl implements TransportMapper {

    @Override
    public TransportResponse toResponse(Transport transport) {
        if ( transport == null ) {
            return null;
        }

        Long id = null;
        TransportType transportType = null;
        String name = null;
        BigDecimal maxWeightKg = null;
        BigDecimal maxVolumeM3 = null;
        BigDecimal speedKmH = null;
        BigDecimal costPerKm = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = transport.getId();
        transportType = transport.getTransportType();
        name = transport.getName();
        maxWeightKg = transport.getMaxWeightKg();
        maxVolumeM3 = transport.getMaxVolumeM3();
        speedKmH = transport.getSpeedKmH();
        costPerKm = transport.getCostPerKm();
        createdAt = transport.getCreatedAt();
        updatedAt = transport.getUpdatedAt();

        TransportResponse transportResponse = new TransportResponse( id, transportType, name, maxWeightKg, maxVolumeM3, speedKmH, costPerKm, createdAt, updatedAt );

        return transportResponse;
    }

    @Override
    public Transport toEntity(TransportCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Transport.TransportBuilder transport = Transport.builder();

        transport.transportType( request.transportType() );
        transport.name( request.name() );
        transport.maxWeightKg( request.maxWeightKg() );
        transport.maxVolumeM3( request.maxVolumeM3() );
        transport.speedKmH( request.speedKmH() );
        transport.costPerKm( request.costPerKm() );

        return transport.build();
    }

    @Override
    public void updateFromRequest(TransportCreateRequest request, Transport transport) {
        if ( request == null ) {
            return;
        }

        transport.setTransportType( request.transportType() );
        transport.setName( request.name() );
        transport.setMaxWeightKg( request.maxWeightKg() );
        transport.setMaxVolumeM3( request.maxVolumeM3() );
        transport.setSpeedKmH( request.speedKmH() );
        transport.setCostPerKm( request.costPerKm() );
    }
}
