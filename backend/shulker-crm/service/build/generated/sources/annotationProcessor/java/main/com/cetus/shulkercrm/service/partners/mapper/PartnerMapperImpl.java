package com.cetus.shulkercrm.service.partners.mapper;

import com.cetus.shulkercrm.api.partners.dto.PartnerCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerResponse;
import com.cetus.shulkercrm.service.partners.entity.Partner;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-20T12:51:26+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class PartnerMapperImpl implements PartnerMapper {

    @Override
    public PartnerResponse toResponse(Partner partner) {
        if ( partner == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String contactEmail = null;
        Boolean isActive = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = partner.getId();
        name = partner.getName();
        contactEmail = partner.getContactEmail();
        isActive = partner.getIsActive();
        createdAt = partner.getCreatedAt();
        updatedAt = partner.getUpdatedAt();

        PartnerResponse partnerResponse = new PartnerResponse( id, name, contactEmail, isActive, createdAt, updatedAt );

        return partnerResponse;
    }

    @Override
    public Partner toEntity(PartnerCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Partner.PartnerBuilder partner = Partner.builder();

        if ( request.isActive() != null ) {
            partner.isActive( request.isActive() );
        }
        else {
            partner.isActive( true );
        }
        partner.name( request.name() );
        partner.apiKey( request.apiKey() );
        partner.contactEmail( request.contactEmail() );

        return partner.build();
    }

    @Override
    public void updateFromRequest(PartnerCreateRequest request, Partner partner) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            partner.setName( request.name() );
        }
        if ( request.apiKey() != null ) {
            partner.setApiKey( request.apiKey() );
        }
        if ( request.contactEmail() != null ) {
            partner.setContactEmail( request.contactEmail() );
        }
        if ( request.isActive() != null ) {
            partner.setIsActive( request.isActive() );
        }
    }
}
