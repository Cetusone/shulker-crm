package com.cetus.shulkercrm.service.inventory.service;


import com.cetus.shulkercrm.api.inventory.dto.StockCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.service.inventory.entity.Product;
import com.cetus.shulkercrm.service.inventory.entity.Stock;
import com.cetus.shulkercrm.service.inventory.entity.StockMovement;
import com.cetus.shulkercrm.service.inventory.repository.ProductRepository;
import com.cetus.shulkercrm.service.inventory.repository.StockMovementRepository;
import com.cetus.shulkercrm.service.inventory.repository.StockRepository;
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
public class StockService {

    private final StockRepository stockRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    @Transactional
    public StockResponse addProductOnWarehouse(Long wareHouseId, StockCreateRequest request) {
        log.info("addProductOnWarehouse, wareHouseId={}, request={}", wareHouseId, request);

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        //TODO: Тута будет проверка на существование склада
        //warehouseClient.checkExists(warehouseId);

        Stock stock = stockRepository.findByOwnWarehouseIdAndProductId(wareHouseId, product.getId())
                .orElseGet(() -> Stock.builder()
                        .ownWarehouseId(wareHouseId)
                        .product(product)
                        .quantity(0)
                        .reservedQuantity(0)
                        .build());
        stock.setQuantity(stock.getQuantity() + request.quantity());
        Stock savedStock = stockRepository.save(stock);

        StockMovement movement = StockMovement.builder()
                .stock(savedStock)
                .movementType(StockMovement.MovementType.REPLENISHMENT)
                .quantityChange(request.quantity())
                .quantityAfter(savedStock.getQuantity())
                .reason(request.reason() != null ? request.reason() : "Размещение товара")
                .build();
        StockMovement savedMovement = stockMovementRepository.save(movement);
        return mapToResponse(savedStock);


    }

    @Transactional(readOnly = true)
    public Page<StockResponse> getAllStocks(Long warehouseId, Pageable pageable) {
        log.info("getAllStocks");
        Page<Stock> stocks =  stockRepository.findAllByOwnWarehouseId(warehouseId, pageable);
        return stocks.map(this::mapToResponse);

    }

    private StockResponse mapToResponse(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getOwnWarehouseId(),
                stock.getProduct().getId(),
                stock.getQuantity(),
                stock.getReservedQuantity(),
                stock.getUpdatedAt()
        );
    }
}
