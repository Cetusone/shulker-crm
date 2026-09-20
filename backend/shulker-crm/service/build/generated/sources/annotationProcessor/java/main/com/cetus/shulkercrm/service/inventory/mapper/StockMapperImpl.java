package com.cetus.shulkercrm.service.inventory.mapper;

import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.entity.Stock;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-20T12:51:26+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockResponse toResponse(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        Long productId = null;
        Long id = null;
        Long ownWarehouseId = null;
        Integer quantity = null;
        Integer reservedQuantity = null;
        LocalDateTime updatedAt = null;

        productId = stockProductId( stock );
        id = stock.getId();
        ownWarehouseId = stock.getOwnWarehouseId();
        quantity = stock.getQuantity();
        reservedQuantity = stock.getReservedQuantity();
        updatedAt = stock.getUpdatedAt();

        StockResponse stockResponse = new StockResponse( id, ownWarehouseId, productId, quantity, reservedQuantity, updatedAt );

        return stockResponse;
    }

    private Long stockProductId(Stock stock) {
        Product product = stock.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }
}
