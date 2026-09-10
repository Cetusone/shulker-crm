package com.cetus.shulkercrm.service.inventory.mapper;

import com.cetus.shulkercrm.api.inventory.dto.CharacteristicDto;
import com.cetus.shulkercrm.api.inventory.dto.CharacteristicResponse;
import com.cetus.shulkercrm.api.inventory.dto.ProductCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.ProductResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.entity.ProductCharacteristic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    CharacteristicResponse toResponse(ProductCharacteristic characteristic);

    @Mapping(target = "characteristics", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductCreateRequest request);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    ProductCharacteristic toEntity(CharacteristicDto dto);

    @Mapping(target = "characteristics", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(ProductCreateRequest request, @MappingTarget Product product);
}
