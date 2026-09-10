package com.cetus.shulkercrm.service.partners.mapper;

import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseCreateRequest;
import com.cetus.shulkercrm.api.partners.dto.PartnerWarehouseResponse;
import com.cetus.shulkercrm.service.partners.entity.PartnerWarehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PartnerWarehouseMapper {

    @Mapping(source = "partner.id", target = "partnerId")
    PartnerWarehouseResponse toResponse(PartnerWarehouse warehouse);

    // партнёра подставляет сервис — он его достаёт из репозитория и проверяет
    @Mapping(target = "partner", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PartnerWarehouse toEntity(PartnerWarehouseCreateRequest request);

    @Mapping(target = "partner", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(PartnerWarehouseCreateRequest request, @MappingTarget PartnerWarehouse warehouse);
}
