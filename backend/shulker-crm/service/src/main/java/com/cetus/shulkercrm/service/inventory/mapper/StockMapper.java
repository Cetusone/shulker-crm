package com.cetus.shulkercrm.service.inventory.mapper;

import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.service.inventory.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StockMapper {

    @Mapping(source = "product.id", target = "productId")
    StockResponse toResponse(Stock stock);
}
