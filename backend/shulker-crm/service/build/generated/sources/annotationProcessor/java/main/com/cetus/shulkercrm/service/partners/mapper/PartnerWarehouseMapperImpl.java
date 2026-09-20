package com.cetus.shulkercrm.service.partners.mapper;

import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import com.cetus.shulkercrm.service.partners.entity.PartnerWarehouse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-20T12:51:26+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class PartnerWarehouseMapperImpl implements PartnerWarehouseMapper {

    @Override
    public PartnerWarehouseResponse toResponse(PartnerWarehouse warehouse) {
        if ( warehouse == null ) {
            return null;
        }

        Long partnerId = null;
        Long id = null;
        String name = null;
        String address = null;
        BigDecimal latitude = null;
        BigDecimal longitude = null;
        Boolean acceptsLand = null;
        Boolean acceptsSea = null;
        Boolean acceptsAir = null;
        Boolean isActive = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        partnerId = warehousePartnerId( warehouse );
        id = warehouse.getId();
        name = warehouse.getName();
        address = warehouse.getAddress();
        latitude = warehouse.getLatitude();
        longitude = warehouse.getLongitude();
        acceptsLand = warehouse.getAcceptsLand();
        acceptsSea = warehouse.getAcceptsSea();
        acceptsAir = warehouse.getAcceptsAir();
        isActive = warehouse.getIsActive();
        createdAt = warehouse.getCreatedAt();
        updatedAt = warehouse.getUpdatedAt();

        PartnerWarehouseResponse partnerWarehouseResponse = new PartnerWarehouseResponse( id, partnerId, name, address, latitude, longitude, acceptsLand, acceptsSea, acceptsAir, isActive, createdAt, updatedAt );

        return partnerWarehouseResponse;
    }

    @Override
    public PartnerWarehouse toEntity(PartnerWarehouseCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        PartnerWarehouse.PartnerWarehouseBuilder partnerWarehouse = PartnerWarehouse.builder();

        partnerWarehouse.name( request.getName() );
        partnerWarehouse.address( request.getAddress() );
        partnerWarehouse.latitude( request.getLatitude() );
        partnerWarehouse.longitude( request.getLongitude() );
        partnerWarehouse.acceptsLand( request.getAcceptsLand() );
        partnerWarehouse.acceptsSea( request.getAcceptsSea() );
        partnerWarehouse.acceptsAir( request.getAcceptsAir() );

        partnerWarehouse.isActive( true );

        return partnerWarehouse.build();
    }

    @Override
    public void updateFromRequest(PartnerWarehouseCreateRequest request, PartnerWarehouse warehouse) {
        if ( request == null ) {
            return;
        }

        warehouse.setName( request.getName() );
        warehouse.setAddress( request.getAddress() );
        warehouse.setLatitude( request.getLatitude() );
        warehouse.setLongitude( request.getLongitude() );
        warehouse.setAcceptsLand( request.getAcceptsLand() );
        warehouse.setAcceptsSea( request.getAcceptsSea() );
        warehouse.setAcceptsAir( request.getAcceptsAir() );
    }

    private Long warehousePartnerId(PartnerWarehouse partnerWarehouse) {
        Partner partner = partnerWarehouse.getPartner();
        if ( partner == null ) {
            return null;
        }
        return partner.getId();
    }
}
