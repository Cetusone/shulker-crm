package com.cetus.shulkercrm.service.logistics.mapper;

import com.cetus.shulkercrm.api.logistics.dto.TransportCreateRequest;
import com.cetus.shulkercrm.api.logistics.dto.TransportResponse;
import com.cetus.shulkercrm.service.logistics.entity.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransportMapper {

    TransportResponse toResponse(Transport transport);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Transport toEntity(TransportCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(TransportCreateRequest request, @MappingTarget Transport transport);
}
