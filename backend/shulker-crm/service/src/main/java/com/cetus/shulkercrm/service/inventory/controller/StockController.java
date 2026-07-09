package com.cetus.shulkercrm.service.inventory.controller;


import com.cetus.shulkercrm.api.inventory.api.StockAPI;
import com.cetus.shulkercrm.api.inventory.dto.StockCreateRequest;
import com.cetus.shulkercrm.api.inventory.dto.StockResponse;
import com.cetus.shulkercrm.service.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class StockController implements StockAPI {

    private final StockService stockService;

    @Override
    public StockResponse addProductOnWarehouse(Long id, StockCreateRequest stockCreateRequest) {
        log.info("Adding product on Warehouse {}", id);
        return stockService.addProductOnWarehouse(id, stockCreateRequest);
    }

    @Override
    public Page<StockResponse> getAllStocks(Long id, Pageable pageable) {
        log.info("Getting stocks from Warehouse {}", id);
        return stockService.getAllStocks(id, pageable);

    }
}
