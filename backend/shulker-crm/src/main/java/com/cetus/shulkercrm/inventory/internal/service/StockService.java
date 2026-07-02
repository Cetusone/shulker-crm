package com.cetus.shulkercrm.inventory.internal.service;


import com.cetus.shulkercrm.inventory.api.StockServiceInterface;
import com.cetus.shulkercrm.inventory.api.dto.StockCreateRequest;
import com.cetus.shulkercrm.inventory.api.dto.StockResponse;
import com.cetus.shulkercrm.inventory.internal.entity.Product;
import com.cetus.shulkercrm.inventory.internal.entity.Stock;
import com.cetus.shulkercrm.inventory.internal.entity.StockMovement;
import com.cetus.shulkercrm.inventory.internal.repository.ProductRepository;
import com.cetus.shulkercrm.inventory.internal.repository.StockMovementRepository;
import com.cetus.shulkercrm.inventory.internal.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class StockService implements StockServiceInterface {

    private final StockRepository stockRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public StockResponse addProductOnWarehouse(Long wareHouseId, StockCreateRequest request) {
        log.info("addProductOnWarehouse, wareHouseId={}, request={}", wareHouseId, request);

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        //TODO: Тута будет проверка на существование склада
        //warehouseClient.checkExists(warehouseId);

        Stock stock = stockRepository.findByWarehouseIdAndProductId(wareHouseId, product.getId())
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

    @Override
    @Transactional(readOnly = true)
    public List<StockResponse> getAllStocks(Long warehouseId) {
        log.info("getAllStocks");
        return stockRepository.findAllByOwnWarehouseId(warehouseId).stream()
                .map(this::mapToResponse)
                .toList();


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
